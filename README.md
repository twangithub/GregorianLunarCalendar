# GregorianLunarCalendar
`GregorianLunarCalendar`提供了农历+公历的日期选择模式，同时支持公历+农历的无缝切换
##说明
###上一个版本
上一个版本的`GregorianLunarCalendar`通过`NumberPicker`构造的日期选择器，可定制性较差，如：<br>
1.每个`NumberPicker`只能显示3个选项;<br>
2.`setValue()`以及滑动时时没有过渡动效;<br>
3.滑动fling的速度较慢等等。<br>

###最新版本
前段时间抽空写了一个`NumberPickerView`控件，算是一个比原生的`NumberPicker`在使用上更加优美的控件，`NumberPickerView`的项目地址在此处：
https://github.com/Carbs0126/NumberPickerView

此版本的`GregorianLunarCalendar`只是使用`NumberPickerView`替换`NumberPicker`，做成了一个更加优美的日期选择控件。
由于我将`NumberPicker`中常用的方法如`setValue()` `setMinValue()` `setMaxValue()` `setDisplayedValues()`等函数均在`NumberPickerView`中实现了，因此移植起来非常简单。
`GregorianLunarCalendar`控件效果如下：

##具有的特性
###UI显示效果的改进：
1.内容的重新设定时可以选择是否添加动效，即从公历转换到农历时，dayPicker肯定是需要重新设置value的，如果设置为添加动效，那么在切换时可以看到滚动效果；<br>
2.在切换年/月时，如果月/日需要改变时（如每个月的天数不同），具有滚动效果，且滚动的路径是选择最短路径；<br>
###具有的功能：
1.公历年月日选择，年月改变时会联动改变相对应的月日时期显示，确保公历日期显示正确；<br>
2.农历年月日选择，同样会有联动改变对应日期的效果，确保日期符合农历历法，包括闰月、大小月等；<br>
3.公历农历互相转换时，实现无缝切换，如2016年2月29日切换为农历则显示二零一六年一月廿二日，农历转公历同样效果；<br>
4.显示范围为1901年-2100年，满足大部分使用需求；<br>
5.在1901年与2100年，确保公历农历转换时的边界限制。<br>


##TODO
由于这种选择样式是滚动选择，因此，不支持设置日期选择区间。如果想要控制日期选择的区间，请使用DatePicker或其他第三方控件，当然我现在正打算写一个DatePicker控件。

