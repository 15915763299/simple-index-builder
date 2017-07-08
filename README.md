# simple-index-builder
CS504 Week6 Homework<br>

这是从搜索广告引擎中剥离出的一段代码，独立运行，用于生成索引，就是英文说的 `Index`，这里分为两种 `Forward Index` 与 `Inverted Index`<br>

**Forward Index —— 正排索引**<br>
> 将数据插入数据库中（这里我们用MySQL），这里我们主要根据广告ID进行索引广告信息

**Inverted Index —— 倒排索引**<br>
> 将数据存入memcached中，以关键词为索引，索引信息为广告ID

#### 1. MySQL --> Windows
* 下载 [MySQL-Windows](https://dev.mysql.com/downloads/windows/installer/5.7.html) 并安装，Workbench与Java关联组件一定要装，不要忘记用户名和密码
* Workbench怎么用呢：
    
      先用Workbench创建你的数据库，就是 `Schema`，具体操作：`File->Nw Model...`
      创建完数据库和表就可以保存了，得到一个 `*.wmb` 文件
      这个文件只是用于记录数据库的结构，此时在你的电脑里还没生成数据库，以后要修改数据库时可以直接打开这个文件进行修改
      对于mwb文件，可以用它生成 `*.sql` 文件，这个文件里面就是具体生成数据库的代码
      然后连上本地的数据库，具体操作：点击Workbench左上角的小房子，然后`Database->Connect to Database...`
      最后把 `*.sql` 中的代码复制过来执行就行了，刷新 `Navigator` 界面你就可以看到你的数据库了<br>
      
* 最后再强调一下： MySQL Workbench produces a `.mwb` file which **is not a database**. This file is only a **Entity-Relationship schema** (which is very useful).<br>

#### 2. Memcached --> Windows
Memcached要在Windows比较麻烦，给两个参考网站：
[Memcached源码安装](http://www.cnblogs.com/skey_chen/p/5757957.html)，[Memcached安装与测试](http://blog.csdn.net/bbirdsky/article/details/26853045)<br>

#### 3. 运行
`*.jar` 文件在 `out/artifacts/simple_index_builder_jar` 目录下，用 `MyDatabase.mwb` 生成数据库，然后启动 `Memcached`，最后运行 `simple-index-builder.jar` 
（cmd: java -jar xxx）
