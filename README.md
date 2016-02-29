# GregorianLunarCalendar
GregorianLunarCalendar提供了农历+公历的日期选择模式，同时支持公历+农历的无缝切换

android自带的DatePicker具有日期选择的功能，但是由于不同的rom可能会对此控件进行深度定制，且扩展性较差，因此如果我们在自己的程序中使用此控件，很可能会在不同的rom上显示不同的效果，这给用户带来了很不好的UI体验。
为了规避上述弊端，我使用了三个NumberPicker组合的形式来显示日期。同时为了兼顾国内的日期使用习惯，我添加了农历选择功能。具体功能概述如下：<br>
1.公历年月日选择，年月改变时会联动改变相对应的月日时期显示，确保公历日期显示正确；<br>
2.农历年月日选择，同样会有联动改变对应日期的效果，确保日期符合农历历法，包括闰月、大小月等；<br>
3.公历农历互相转换时，实现无缝切换，如2016年2月29日切换为农历则显示二零一六年一月廿二日，农历转公历同样效果；<br>
4.显示范围为1901年-2100年，满足大部分使用需求；<br>
5.在1901年与2100年，确保公历农历转换时的边界限制。<br>

待完善之处：<br>
1.农历、公历转换时没有动画效果；<br>
2.内容上下滑动时没有添加渐变效果；<br>
3.使用NumberPickerUI可定制性较差；<br>

###动画示例：<br>
![GregorianLunarCalendar](https://github.com/Carbs0126/GregorianLunarCalendar/blob/master/GregorianLunarCalendar.gif)
