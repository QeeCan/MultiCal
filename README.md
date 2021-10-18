# MultiCal
## 系统内容：
基于Android的多功能日历软件，包含日历、日程、便签、记账和星座五个模块，可以在对应的模块完成相应的信息查看和管理需要。
## 技术核心：
第三方的calendar组件+Intent和Bundle完成信息传递+ListView完成信息显示+SQLite完成数据存储+Okhttp获取网络星座的API
## 实现流程：
对日历模块，通过引入第三方Calendar组件模块完成日历的显示，通过查看日历的月视图获取用户所需的阳历、阴历和节日信息，对于日程、便签和记账等几个模块，主要为展示相应列表提供所有记录给用户进行查看，新建、编辑、搜索和删除等功能。对于需要输入的部分可以使用EditText完成，以Listview的形式展示全部的标题及内容的简略信息。
在列表页使用Intent和Bundle完成日程、便签、记账等信息的传递并通过ListView进行显示，使用FloatingActionButton为用户提供新建便签的功能；对ListView中的具体条目进行点击的监听操作可以跳转到具体的编辑页面对其进行修改；使用SearchView绘制搜索框完成搜索；对ListView中的具体条目进行长按的点击监听操作可以弹出对话框对点击的信息条目进行删除。并且使用Intent和Bundle完成日程、便签、记账等信息的传递并通过ListView进行显示，涉及到的所有数据经由SQLite完成相关的数据的管理和存储。星座部分主要提供星座运势的查询，使用Okhttp3来完成对网络的连接与请求，获取每日星座运势信息，采用聚合数据提供的星座运势API得到数据，将GridView对应组件初始化，获取StarBean类名称信息的数据源，创建并设置适配器StarAdapter，使得星座的图标得到显示。然后使用OkHttp的Get方法连接聚合数据提供的API获取Json格式的数据，使用Gson进行解析并整理到LuckBean集合中。使用LuckTypeBean封装显示相关的标题、内容和颜色，并得到相关的数据信息，在LuckActivity中查找并完成相关控件的初始化，使用LuckAdapter完成星座运势的显示。
