
# 一个ArrayList的操作流, 用法与stream()很像.

### 为什么会有这个文件?
因为stream()有些api有些不符合我的需求,而java无法为已有类添加拓展方法限制了我只能自己构建一个流操作.
我有个业务需要一个firstWhere找出某个符合条件的元素, 元素可为空, stream我没有找到这个API,所以当我看到java的@FunctionalInterface这个语法的时候就决定写一个.


我是一个iOS开发,我习惯了swift提供给我的那些API语法

比如
* firstWhere 条件判断筛选元素
* prefix 筛选前几个
* suffix 帅选后几个
* contain 条件判断是否包含某个元素
* Interger 数组separator join 变成分隔符字符串
* map
* filter
* compactMap等等

### 为什么只有一个文件?
没有demo,是因为我希望大家有空可以看看源码,源码真的简单的要死.

### API设计的小建议
pipe的链式调用
当下一个输出有可能是数组的时候,那他必须是SequenceOperator的返回
当下个输出是单个元素的时候,可以直接返回

### 帖个简单的用例
```
List<Integer> list = Arrays.asList(2, 3, 4, 5, 6);
                String idss = SequenceOperator.build(list)
                        .map(Object::toString)
                        .map(Integer::parseInt)
                        .filter((e) -> e % 2 == 0)
                        .join(",");
                        
List<Integer> collect = SequenceOperator.build(list)
                        .map((e) -> e + 1)
                        .collect();
```

### PS
stream()的api绝大部分情况下都是十分强大的,所以如果没有我这种特殊需求的话,还是用stream()的API更安全点,java官方代码的安全性绝对远高于我这种简单写法一万倍.