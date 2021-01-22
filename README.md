# aosp_crypto_logger

监控java层的加密算法一个ROM（目前不支持RSA)


基于https://github.com/icew4y/crypto_filter_aosp项目基础上增加了功能：
- 增加logcat日志。
- 支持同时对多个app进行监控。

**注意** 代码可能有bug,不保证稳定运行。

源码基于AOSP 8.1.0_r1上修改。

## 编译

```
make systemimage

```

## 刷机

```
adb reboot bootloader
fastboot flash system system.img
fastboot reboot
```


## 用法

把需要监控加密算法的app包名写入/data/local/tmp/monitor_package
```
adb shell "echo com.example.test >  /data/local/tmp/monitor_package"
adb shell "echo com.hookme >>  /data/local/tmp/monitor_package"
```
查看logcat日志：
```
adb logcat -s "fishso"
```
示例日志：
```
01-21 19:30:59.319  3187  3187 I fishso  : monitor_package: com.example.test,com.hookme,
01-21 19:30:59.326  3187  3187 I fishso  : filepath:/data/data/com.hookme/MessageDigest,content:MessageDigestTag:{"Algorithm":"MD5","Provider":"AndroidOpenSSL","data":"Hello World!","Base64Data":"SGVsbG8gV29ybGQh","digest":"ed076287532e86365e841e92bfc50d8c","StackTrace":"ZGFsdmlrLnN5c3RlbS5WTVN0YWNrLmdldFRocmVhZFN0YWNrVHJhY2UoKSAtMiA8LSAKamF2YS5sYW5nLlRocmVhZC5nZXRTdGFja1RyYWNlKCkgMSw1MzggPC0gCmphdmEuc2VjdXJpdHkuTWVzc2FnZURpZ2VzdC5kaWdlc3QoKSA2NDEgPC0gCmphdmEuc2VjdXJpdHkuTWVzc2FnZURpZ2VzdC5kaWdlc3QoKSA3NjYgPC0gCmNvbS5ob29rbWUudGVzdC5NeUNyeXB0by5tZDUoKSAxNjEgPC0gCmNvbS5ob29rbWUudGVzdC5NeUNyeXB0by5tZDVCYXNlNjQoKSAxNTEgPC0gCmNvbS5ob29rbWUuTWFpbkFjdGl2aXR5LnRlc3RNRDUoKSA2NSA8LSAKY29tLmhvb2ttZS5NYWluQWN0aXZpdHkkMS5vbkNsaWNrKCkgNDUgPC0gCmFuZHJvaWQudmlldy5WaWV3LnBlcmZvcm1DbGljaygpIDYsMjk0IDwtIAphbmRyb2lkLnZpZXcuVmlldyRQZXJmb3JtQ2xpY2sucnVuKCkgMjQsNzcwIDwtIAphbmRyb2lkLm9zLkhhbmRsZXIuaGFuZGxlQ2FsbGJhY2soKSA3OTAgPC0gCmFuZHJvaWQub3MuSGFuZGxlci5kaXNwYXRjaE1lc3NhZ2UoKSA5OSA8LSAKYW5kcm9pZC5vcy5Mb29wZXIubG9vcCgpIDE2NCA8LSAKYW5kcm9pZC5hcHAuQWN0aXZpdHlUaHJlYWQubWFpbigpIDYsNDk0IDwtIApqYXZhLmxhbmcucmVmbGVjdC5NZXRob2QuaW52b2tlKCkgLTIgPC0gCmNvbS5hbmRyb2lkLmludGVybmFsLm9zLlJ1bnRpbWVJbml0JE1ldGhvZEFuZEFyZ3NDYWxsZXIucnVuKCkgNDM4IDwtIApjb20uYW5kcm9pZC5pbnRlcm5hbC5vcy5aeWdvdGVJbml0Lm1haW4oKSA4MDc="}
01-21 19:30:59.333  3187  3187 I fishso  : monitor_package: com.example.test,com.hookme,
01-21 19:30:59.339  3187  3187 I fishso  : filepath:/data/data/com.hookme/Cipher,content:CipherTag:{"opmode":"ENCRYPT_MODE","key":"0123456789123456","Key(Base64)":"MDEyMzQ1Njc4OTEyMzQ1Ng==","algorithm":"AES","SecureRandom":"SHA1PRNG","iv":"GSYHMBANKAESIVGS","Iv(Base64)":"R1NZSE1CQU5LQUVTSVZHUw==","provider":"BC","transformation":"AES\/CFB\/PKCS5Padding","data":"Hello World!","Base64Data":"SGVsbG8gV29ybGQh","doFinal":"BP8YZfYieVFRjR11cDrwHA==","Base64Cipher":"BP8YZfYieVFRjR11cDrwHA==","StackTrace":"ZGFsdmlrLnN5c3RlbS5WTVN0YWNrLmdldFRocmVhZFN0YWNrVHJhY2UoKSAtMiA8LSAKamF2YS5sYW5nLlRocmVhZC5nZXRTdGFja1RyYWNlKCkgMSw1MzggPC0gCmphdmF4LmNyeXB0by5DaXBoZXIuZG9GaW5hbCgpIDIsMDA4IDwtIApjb20uaG9va21lLnRlc3QuTXlDcnlwdG8uZW5jcnlwdEFFUygpIDM5IDwtIApjb20uaG9va21lLk1haW5BY3Rpdml0eS50ZXN0QUVTKCkgODkgPC0gCmNvbS5ob29rbWUuTWFpbkFjdGl2aXR5JDEub25DbGljaygpIDQ3IDwtIAphbmRyb2lkLnZpZXcuVmlldy5wZXJmb3JtQ2xpY2soKSA2LDI5NCA8LSAKYW5kcm9pZC52aWV3LlZpZXckUGVyZm9ybUNsaWNrLnJ1bigpIDI0LDc3MCA8LSAKYW5kcm9pZC5vcy5IYW5kbGVyLmhhbmRsZUNhbGxiYWNrKCkgNzkwIDwtIAphbmRyb2lkLm9zLkhhbmRsZXIuZGlzcGF0Y2hNZXNzYWdlKCkgOTkgPC0gCmFuZHJvaWQub3MuTG9vcGVyLmxvb3AoKSAxNjQgPC0gCmFuZHJvaWQuYXBwLkFjdGl2aXR5VGhyZWFkLm1haW4oKSA2LDQ5NCA8LSAKamF2YS5sYW5nLnJlZmxlY3QuTWV0aG9kLmludm9rZSgpIC0yIDwtIApjb20uYW5kcm9pZC5pbnRlcm5hbC5vcy5SdW50aW1lSW5pdCRNZXRob2RBbmRBcmdzQ2FsbGVyLnJ1bigpIDQzOCA8LSAKY29tLmFuZHJvaWQuaW50ZXJuYWwub3MuWnlnb3RlSW5pdC5tYWluKCkgODA3"}
01-21 19:31:07.156  2624  2624 I fishso  : monitor_package: com.example.test,com.hookme,
01-21 19:31:07.190  2624  2624 I fishso  : filepath:/data/data/com.example.test/MessageDigest,content:MessageDigestTag:{"Algorithm":"MD5","Provider":"AndroidOpenSSL","data":"hello","Base64Data":"aGVsbG8=","digest":"5d41402abc4b2a76b9719d911017c592","StackTrace":"ZGFsdmlrLnN5c3RlbS5WTVN0YWNrLmdldFRocmVhZFN0YWNrVHJhY2UoKSAtMiA8LSAKamF2YS5sYW5nLlRocmVhZC5nZXRTdGFja1RyYWNlKCkgMSw1MzggPC0gCmphdmEuc2VjdXJpdHkuTWVzc2FnZURpZ2VzdC5kaWdlc3QoKSA2NDEgPC0gCmNvbS51dGlscy5kZWNyeXB0Lk15Q3J5cHRvLm1kNV8yKCkgMjEwIDwtIApjb20uZXhhbXBsZS50ZXN0Lk1haW5BY3Rpdml0eSQxLm9uQ2xpY2soKSA1MiA8LSAKYW5kcm9pZC52aWV3LlZpZXcucGVyZm9ybUNsaWNrKCkgNiwyOTQgPC0gCmFuZHJvaWQudmlldy5WaWV3JFBlcmZvcm1DbGljay5ydW4oKSAyNCw3NzAgPC0gCmFuZHJvaWQub3MuSGFuZGxlci5oYW5kbGVDYWxsYmFjaygpIDc5MCA8LSAKYW5kcm9pZC5vcy5IYW5kbGVyLmRpc3BhdGNoTWVzc2FnZSgpIDk5IDwtIAphbmRyb2lkLm9zLkxvb3Blci5sb29wKCkgMTY0IDwtIAphbmRyb2lkLmFwcC5BY3Rpdml0eVRocmVhZC5tYWluKCkgNiw0OTQgPC0gCmphdmEubGFuZy5yZWZsZWN0Lk1ldGhvZC5pbnZva2UoKSAtMiA8LSAKY29tLmFuZHJvaWQuaW50ZXJuYWwub3MuUnVudGltZUluaXQkTWV0aG9kQW5kQXJnc0NhbGxlci5ydW4oKSA0MzggPC0gCmNvbS5hbmRyb2lkLmludGVybmFsLm9zLlp5Z290ZUluaXQubWFpbigpIDgwNw=="}
```
