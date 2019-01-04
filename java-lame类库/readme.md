<<<<<<< HEAD
###此文为原创，转载请征得本人同意并且在显眼位置标明本文链接

最近一个项目，需要使用java将未处理的raw格式转为mp3格式，网上找了好多案例均不可转raw格式，连格式工厂都不可以转。这个格式真的是太古老了，无奈之下老板亲自动手写了一段代码，通过jni之后调用命令行来进行转换，此文记录一路踩坑填坑，无兴趣者可直接跳转**文末**下载最终代码。
话不多说，下面上代码。
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
此段代码即可实现通过lame.exe将raw格式转为mp3。
可将*command*在命令行运行即可观察效果。
但此时会有一个问题即为 *process*为异步执行，在运行时此段代码时，raw文件正在进行转换操作，但是后面代码已经需要调用转换完成的MP3文件，此时会拿不到文件。一顿谷歌之后，发现有大神给出解决办法，
```
 int waitFor = process.waitFor();
```
在执行完*process*后，调用此行代码，则可使程序等待该方法执行完成之后再继续运行。但此时会出现程序到此步骤则停止运行问题，查询资料后发现问题。
>**jdk官方文档**
如有必要，一直要等到由该 Process 对象表示的进程已经终止。如果已终止该子进程，此方法立即返回。但是直接调用这个方法会导致当前线程阻塞，直到退出子进程。
因为本地的系统对标准输入和输出所提供的缓冲池有效，所以错误的对标准输出快速的写入何从标准输入快速的读入都有可能造成子进程的所，甚至死锁。

简单来说，就是主进程需要等待子进程*process*方法执行完成，所以主进程调用*Process.waitfor*等待子进程完成。子进程的过程就是不断打印信息（在命令行执行可以查看），主进程中可以通过Process.getInputStream和Process.getErrorStream来进行查看。
此时，子进程不断向主进程发送数据，但此时主进程已经挂起，当子进程和主进程之间缓冲区塞满后，子进程无法发送数据，也会挂起。
这样主进程等待子进程结束执行，子进程等待主进程处理数据，双方互相等待，最终导致死锁。

所以要解决如上问题，只需要在主进程挂起之前，不断处理子进程发送的数据就可以避免此种情况，所以我们在挂起之前再开启如下两个进程。
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
此时该段代码则可使用。此bug据我所知目前尚无其他解决办法。如有需要只能先如此使用。
[lame.exe及完整代码下载](https://pan.baidu.com/s/1y5QXHG3wdss_m52M85EsUw):mxno

[简书地址](https://www.jianshu.com/p/cc700da8af9e)

如果能有个star我是超级开心的了。
###此文为原创，转载请征得本人同意并且在显眼位置标明本文链接

=======
###此文为原创，转载请征得本人同意并且在显眼位置标明本文链接

最近一个项目，需要使用java将未处理的raw格式转为mp3格式，网上找了好多案例均不可转raw格式，连格式工厂都不可以转。这个格式真的是太古老了，无奈之下老板亲自动手写了一段代码，通过jni之后调用命令行来进行转换，此文记录一路踩坑填坑，无兴趣者可直接跳转**文末**下载最终代码。
话不多说，下面上代码。
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
此段代码即可实现通过lame.exe将raw格式转为mp3。
可将*command*在命令行运行即可观察效果。
但此时会有一个问题即为 *process*为异步执行，在运行时此段代码时，raw文件正在进行转换操作，但是后面代码已经需要调用转换完成的MP3文件，此时会拿不到文件。一顿谷歌之后，发现有大神给出解决办法，
```
 int waitFor = process.waitFor();
```
在执行完*process*后，调用此行代码，则可使程序等待该方法执行完成之后再继续运行。但此时会出现程序到此步骤则停止运行问题，查询资料后发现问题。
>**jdk官方文档**
如有必要，一直要等到由该 Process 对象表示的进程已经终止。如果已终止该子进程，此方法立即返回。但是直接调用这个方法会导致当前线程阻塞，直到退出子进程。
因为本地的系统对标准输入和输出所提供的缓冲池有效，所以错误的对标准输出快速的写入何从标准输入快速的读入都有可能造成子进程的所，甚至死锁。

简单来说，就是主进程需要等待子进程*process*方法执行完成，所以主进程调用*Process.waitfor*等待子进程完成。子进程的过程就是不断打印信息（在命令行执行可以查看），主进程中可以通过Process.getInputStream和Process.getErrorStream来进行查看。
此时，子进程不断向主进程发送数据，但此时主进程已经挂起，当子进程和主进程之间缓冲区塞满后，子进程无法发送数据，也会挂起。
这样主进程等待子进程结束执行，子进程等待主进程处理数据，双方互相等待，最终导致死锁。

所以要解决如上问题，只需要在主进程挂起之前，不断处理子进程发送的数据就可以避免此种情况，所以我们在挂起之前再开启如下两个进程。
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
此时该段代码则可使用。此bug据我所知目前尚无其他解决办法。如有需要只能先如此使用。
[lame.exe及完整代码下载](https://pan.baidu.com/s/1y5QXHG3wdss_m52M85EsUw):mxno
[简书地址](https://www.jianshu.com/p/cc700da8af9e)
如果能有个star我是超级开心的了。
###此文为原创，转载请征得本人同意并且在显眼位置标明本文链接

>>>>>>> 28c8ffa52a4493f32c4ad40d02259a49c2c31c33
