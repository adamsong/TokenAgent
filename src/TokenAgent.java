import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.nio.charset.StandardCharsets;
import java.security.ProtectionDomain;

public class TokenAgent implements ClassFileTransformer {
	public static void premain(String agentArgs, Instrumentation instrumentation) {
		var args = agentArgs.split(":");
		if (args.length != 2) {
			throw new IllegalArgumentException("TokenAgent must be invoked with oldtoken:newtoken as arguments");
		}
		System.out.printf("Preparing to replace %s with %s\n", args[0], args[1]);
		var oldBytes = args[0].getBytes(StandardCharsets.UTF_8);
		var newBytes = args[1].getBytes(StandardCharsets.UTF_8);
		if (oldBytes.length != 60 || newBytes.length != 60) {
			throw new IllegalArgumentException("Tokens should be 60 bytes");
		}
		instrumentation.addTransformer(new TokenAgent(oldBytes, newBytes));
	}
	
	private final byte[] oldBytes;
	private final byte[] newBytes;
	
	public TokenAgent(byte[] oldBytes, byte[] newBytes) {
		this.oldBytes = oldBytes;
		this.newBytes = newBytes;
	}
	
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
	                        ProtectionDomain protectionDomain,
	                        byte[] classfileBuffer) {
		if(!className.equals("com/atlauncher/utils/CurseForgeApi")) return null;
		System.out.println("CurseForgeApi is being transformed\n");
		var newBuffer = new byte[classfileBuffer.length];
		var foundAt = -1;
		
		for (var i = 0; i < classfileBuffer.length; i++) {
			if(foundAt == -1) {
				foundAt = i;
				for (var j = 0; j < oldBytes.length; j++) {
					if(classfileBuffer[i + j] != oldBytes[j]) {
						foundAt = -1;
						break;
					}
				}
			}
			if(foundAt != -1 && i - foundAt < 60) {
				newBuffer[i] = newBytes[i - foundAt];
			} else {
				newBuffer[i] = classfileBuffer[i];
			}
		}
		return newBuffer;
	}
}
