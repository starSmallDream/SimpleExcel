# SimpleExcel
方便操作Excel,可同时对多Sheet进行操作,多个Excel对象的Sheet之前的操作是隔离的(需要使用者保证操作WordBook时必须是串行化的)

> Excel简化操作的工具,基于Apache的POI 4.x进行包装开发

# 因大陆网络制裁原因，可以访问 https://gitee.com/XingXiaoMeng/SimpleExcel 进行获取更新

# 支持的功能点
* 基于Excel文件，复制单元格行
* 多Sheet写入Excel，可根据需要自定义实现单元格对象；支持自定义单元格的样式信息(可以根据数值进行不同层度上的配色)
* 多Sheet读取Excel，基于列索引读取数据并填充列表,支持合并单元格读取值
* 基于列值自动合并列级的单元格，需要简易配置
* 基于Class实现功能点,方便自定义实现化
* 读取Excel,基于Header的名称读取并填充数据
* 读取Excel,校验表头

# 待实现的功能点
* 特定不变的值缓存处理，对应的Handler只调用一次
* 基于SpringBoot形式的自动配置,添加全局变量
* 

# 使用方法
参考 src/test 下的单元测试方法即可


# 其他信息
如果有建议或者问题,请提交 issues ,谢谢您的问题与建议

