$(function () {
    $.get("/statistics",function (data) {
        var infoList=data.split("#");
        new Vue({
            el:"#info",
            data:{
                numOfUsers:infoList[0],
                numOfTasks:infoList[1],
                numOfDoing:infoList[2],
                numOfFinished:infoList[3]
            }
        });

        var myChart1=echarts.init(document.getElementById('main1'));

        var option1 = {
            title : {
                text: '某站点用户访问来源',
                subtext: '纯属虚构',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['植物类','动物类','人物类','物品类','其他']
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:5, name:'植物类'},
                        {value:5, name:'动物类'},
                        {value:4, name:'人物类'},
                        {value:1, name:'物品类'},
                        {value:4, name:'其他'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        myChart1.setOption(option1);


        var myChart2=echarts.init(document.getElementById('main2'));

        var option2 = {
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'标注人物',
                    type:'bar',
                    barWidth: '60%',
                    data:[1, 3, 0, 0, 0, 12, 3]
                }
            ]
        };

        myChart2.setOption(option2);
    })
});

