<<<<<<< HEAD
###����Ϊԭ����ת�������ñ���ͬ�Ⲣ��������λ�ñ�����������

���һ����Ŀ����Ҫʹ��java��δ�����raw��ʽתΪmp3��ʽ���������˺öస��������תraw��ʽ������ʽ������������ת�������ʽ�����̫�����ˣ�����֮���ϰ����Զ���д��һ�δ��룬ͨ��jni֮�����������������ת�������ļ�¼һ·�ȿ���ӣ�����Ȥ�߿�ֱ����ת**��ĩ**�������մ��롣
������˵�������ϴ��롣
```
public static void main(String[] args) {
    String rawPath = "F:/raw.raw";
    String mp3Path = rawPath.replace(".raw", ".mp3");
try {  
  String command = "F:/lame.exe  -r "+rawPath+" -s 15 -m m " + mp3Path;  
  Process process = Runtime.getRuntime().exec(command);
} catch (Throwable e) {  
  e.printStackTrace();  
 } 
```
�˶δ��뼴��ʵ��ͨ��lame.exe��raw��ʽתΪmp3��
�ɽ�*command*�����������м��ɹ۲�Ч����
����ʱ����һ�����⼴Ϊ *process*Ϊ�첽ִ�У�������ʱ�˶δ���ʱ��raw�ļ����ڽ���ת�����������Ǻ�������Ѿ���Ҫ����ת����ɵ�MP3�ļ�����ʱ���ò����ļ���һ�ٹȸ�֮�󣬷����д����������취��
```
 int waitFor = process.waitFor();
```
��ִ����*process*�󣬵��ô��д��룬���ʹ����ȴ��÷���ִ�����֮���ټ������С�����ʱ����ֳ��򵽴˲�����ֹͣ�������⣬��ѯ���Ϻ������⡣
>**jdk�ٷ��ĵ�**
���б�Ҫ��һֱҪ�ȵ��ɸ� Process �����ʾ�Ľ����Ѿ���ֹ���������ֹ���ӽ��̣��˷����������ء�����ֱ�ӵ�����������ᵼ�µ�ǰ�߳�������ֱ���˳��ӽ��̡�
��Ϊ���ص�ϵͳ�Ա�׼�����������ṩ�Ļ������Ч�����Դ���ĶԱ�׼������ٵ�д��δӱ�׼������ٵĶ��붼�п�������ӽ��̵���������������

����˵��������������Ҫ�ȴ��ӽ���*process*����ִ����ɣ����������̵���*Process.waitfor*�ȴ��ӽ�����ɡ��ӽ��̵Ĺ��̾��ǲ��ϴ�ӡ��Ϣ����������ִ�п��Բ鿴�����������п���ͨ��Process.getInputStream��Process.getErrorStream�����в鿴��
��ʱ���ӽ��̲����������̷������ݣ�����ʱ�������Ѿ����𣬵��ӽ��̺�������֮�仺�����������ӽ����޷��������ݣ�Ҳ�����
���������̵ȴ��ӽ��̽���ִ�У��ӽ��̵ȴ������̴������ݣ�˫������ȴ������յ���������

����Ҫ����������⣬ֻ��Ҫ�������̹���֮ǰ�����ϴ����ӽ��̷��͵����ݾͿ��Ա��������������������ڹ���֮ǰ�ٿ��������������̡�
```
	new Thread() {
				@Override
				public void run() {
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;

					try {
						while ((line = in.readLine()) != null) {
							// System.out.println("output: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line = null;

					try {
						while ((line = err.readLine()) != null) {
							// System.out.println("err: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							
							err.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}.start();
```
��ʱ�öδ������ʹ�á���bug������֪Ŀǰ������������취��������Ҫֻ�������ʹ�á�
[lame.exe��������������](https://pan.baidu.com/s/1y5QXHG3wdss_m52M85EsUw):mxno

[�����ַ](https://www.jianshu.com/p/cc700da8af9e)

������и�star���ǳ������ĵ��ˡ�
###����Ϊԭ����ת�������ñ���ͬ�Ⲣ��������λ�ñ�����������

=======
###����Ϊԭ����ת�������ñ���ͬ�Ⲣ��������λ�ñ�����������

���һ����Ŀ����Ҫʹ��java��δ�����raw��ʽתΪmp3��ʽ���������˺öస��������תraw��ʽ������ʽ������������ת�������ʽ�����̫�����ˣ�����֮���ϰ����Զ���д��һ�δ��룬ͨ��jni֮�����������������ת�������ļ�¼һ·�ȿ���ӣ�����Ȥ�߿�ֱ����ת**��ĩ**�������մ��롣
������˵�������ϴ��롣
```
public static void main(String[] args) {
    String rawPath = "F:/raw.raw";
    String mp3Path = rawPath.replace(".raw", ".mp3");
try {  
  String command = "F:/lame.exe  -r "+rawPath+" -s 15 -m m " + mp3Path;  
  Process process = Runtime.getRuntime().exec(command);
} catch (Throwable e) {  
  e.printStackTrace();  
 } 
```
�˶δ��뼴��ʵ��ͨ��lame.exe��raw��ʽתΪmp3��
�ɽ�*command*�����������м��ɹ۲�Ч����
����ʱ����һ�����⼴Ϊ *process*Ϊ�첽ִ�У�������ʱ�˶δ���ʱ��raw�ļ����ڽ���ת�����������Ǻ�������Ѿ���Ҫ����ת����ɵ�MP3�ļ�����ʱ���ò����ļ���һ�ٹȸ�֮�󣬷����д����������취��
```
 int waitFor = process.waitFor();
```
��ִ����*process*�󣬵��ô��д��룬���ʹ����ȴ��÷���ִ�����֮���ټ������С�����ʱ����ֳ��򵽴˲�����ֹͣ�������⣬��ѯ���Ϻ������⡣
>**jdk�ٷ��ĵ�**
���б�Ҫ��һֱҪ�ȵ��ɸ� Process �����ʾ�Ľ����Ѿ���ֹ���������ֹ���ӽ��̣��˷����������ء�����ֱ�ӵ�����������ᵼ�µ�ǰ�߳�������ֱ���˳��ӽ��̡�
��Ϊ���ص�ϵͳ�Ա�׼�����������ṩ�Ļ������Ч�����Դ���ĶԱ�׼������ٵ�д��δӱ�׼������ٵĶ��붼�п�������ӽ��̵���������������

����˵��������������Ҫ�ȴ��ӽ���*process*����ִ����ɣ����������̵���*Process.waitfor*�ȴ��ӽ�����ɡ��ӽ��̵Ĺ��̾��ǲ��ϴ�ӡ��Ϣ����������ִ�п��Բ鿴�����������п���ͨ��Process.getInputStream��Process.getErrorStream�����в鿴��
��ʱ���ӽ��̲����������̷������ݣ�����ʱ�������Ѿ����𣬵��ӽ��̺�������֮�仺�����������ӽ����޷��������ݣ�Ҳ�����
���������̵ȴ��ӽ��̽���ִ�У��ӽ��̵ȴ������̴������ݣ�˫������ȴ������յ���������

����Ҫ����������⣬ֻ��Ҫ�������̹���֮ǰ�����ϴ����ӽ��̷��͵����ݾͿ��Ա��������������������ڹ���֮ǰ�ٿ��������������̡�
```
	new Thread() {
				@Override
				public void run() {
					BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = null;

					try {
						while ((line = in.readLine()) != null) {
							// System.out.println("output: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

			new Thread() {
				@Override
				public void run() {
					BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
					String line = null;

					try {
						while ((line = err.readLine()) != null) {
							// System.out.println("err: " + line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							
							err.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}.start();
```
��ʱ�öδ������ʹ�á���bug������֪Ŀǰ������������취��������Ҫֻ�������ʹ�á�
[lame.exe��������������](https://pan.baidu.com/s/1y5QXHG3wdss_m52M85EsUw):mxno
[�����ַ](https://www.jianshu.com/p/cc700da8af9e)
������и�star���ǳ������ĵ��ˡ�
###����Ϊԭ����ת�������ñ���ͬ�Ⲣ��������λ�ñ�����������

>>>>>>> 28c8ffa52a4493f32c4ad40d02259a49c2c31c33
