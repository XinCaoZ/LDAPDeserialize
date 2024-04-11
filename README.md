### LDAPDeserialize

此GUI工具为https://github.com/C3P0ooo/LDAPUniserial-tool的图形化版本

### 插件模式

	可将此项目加入到burpsuite插件当中使用

![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/4541948d-60c1-454d-85db-8a7f51bb9761)


在菜单栏选择使用

![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/4b618ba6-1a38-45d9-947a-f1ba18b3205b)


### 模式选择

#### self选项（对应gui自定义选项）

可选择输入自定义的base64编码后的序列化数据或指定保存序列化链的文件

![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/2ca607ed-64bb-4d44-83b3-a604e713af6b)


![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/c71b2e68-00a4-4e88-8a30-601a44366f85)


也可使用HTTP服务将文件挂载，会在运行目录下自动生成ser.class文件将传入的内容或文件保存进去

#### Gadget

Gadget模式为内置好的部分序列化链，可直接进行使用（由于burp高版本不兼容8版本链的生成，目前只能在GUI中使用）

设置好java运行环境为java8版本

commend输入命令

```shell
open -a /System/Applications/Calculator.app/Contents/MacOS/Calculator
```

![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/aed712b8-4e2d-4f7e-b436-9889f0b8a547)


![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/f33853e2-80f4-422d-af1a-b45974955326)


![image](https://github.com/XinCaoZ/LDAPDeserialize/assets/64942080/f547afe8-fe02-4a34-8bf1-e3b6a7f521b0)

