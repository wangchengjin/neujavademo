/**
 * Created by jizhong on 2017/8/16.
 */

var myChart = echarts.init(document.getElementById('mainChart'));
myChart.showLoading();

var data = [];
var data2 = [];
var data3 = [];
var data4 = [];
option = {
    title: {
        text: '风速、21#风机功率、结冰情况'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            var tparam = params[0];
            var date = new Date(tparam.name);

            var tipstr = '<div>时间:' + date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds()
                + '</div><div>风速:' + tparam.value[1]
                + '</div><div>功率:' + params[1].value[1]
                + '</div><div>';
            if (params[2].value[1] == 1) {
                tipstr += '无结冰</div>';
            } else {
                tipstr += '有结冰</div>';
            }
            return tipstr;
        },
        axisPointer: {
            animation: false
        }
    },
    legend: {
        data: ['风速数据', '功率数据', '无结冰', '结冰']
        /*textStyle: {
         color: '#ccc'
         }*/
    },
    xAxis: {
        type: 'time',
        splitLine: {
            //show: false
        },
        name: '时间',
        nameLocation: 'middle',
        nameGap: 35,
        nameTextStyle: {fontWeight: 'lighter', fontSize: 13}
    },
    yAxis: {
        name: '风速、功率 、结冰',
        nameGap: 50,
        nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
        nameLocation: 'middle',
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            //show: false
        }
    },
    series: [{
        name: '风速数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }, {
        name: '功率数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data2
    }, {
        name: '无结冰',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data3
    }, {
        name: '结冰',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data4
    }]
};


var begin = 0;

$.get('/data/top1000').done(function (mapData) {
    for (var i = 0; i < mapData.length; i++) {
        var theTime = new Date(mapData[i].time);
        var timeStr = theTime.toString();
        data.push({
            name: timeStr,
            value: [
                //theTime.getFullYear() + '/' + (theTime.getMonth() + 1) + '/' + theTime.getDate() + ' ' + theTime.getHours() + ':' + theTime.getMinutes() + ':' + theTime.getSeconds(),
                theTime,
                mapData[i].windSpeed
            ]
        });
        data2.push({
            name: timeStr,
            value: [
                //theTime.getFullYear() + '/' + (theTime.getMonth() + 1) + '/' + theTime.getDate() + ' ' + theTime.getHours() + ':' + theTime.getMinutes() + ':' + theTime.getSeconds(),
                theTime,
                mapData[i].power
            ]
        });

        if (mapData[i].label == "normal") {
            data3.push({
                name: timeStr,
                value: [
                    theTime,
                    1
                ]
            });
            data4.push({
                name: timeStr,
                value: [
                    theTime,
                    '-'
                ]
            });
        } else {
            data3.push({
                name: timeStr,
                value: [
                    theTime,
                    '-'
                ]
            });
            data4.push({
                name: timeStr,
                value: [
                    theTime,
                    1
                ]
            });
        }

    }
    option.series[0].data = data;
    option.series[1].data = data2;
    option.series[2].data = data3;
    option.series[3].data = data4;

    myChart.setOption(option);
    myChart.hideLoading();

    setInterval(function () {
        begin += 60;
        $.get('/data/nextOne?offset=' + begin).done(function (rdata) {
            for (var i = 0; i < rdata.length; i++) {
                var theTime = new Date(rdata[i].time);
                var timeStr = theTime.toString();
                data.shift();
                data.push({
                    name: timeStr,
                    value: [
                        //theTime.getFullYear() + '/' + (theTime.getMonth() + 1) + '/' + theTime.getDate() + ' ' + theTime.getHours() + ':' + theTime.getMinutes() + ':' + theTime.getSeconds(),
                        theTime,
                        rdata[i].windSpeed
                    ]
                });

                data2.shift();
                data2.push({
                    name: timeStr,
                    value: [
                        //theTime.getFullYear() + '/' + (theTime.getMonth() + 1) + '/' + theTime.getDate() + ' ' + theTime.getHours() + ':' + theTime.getMinutes() + ':' + theTime.getSeconds(),
                        theTime,
                        rdata[i].power
                    ]
                });

                data3.shift();
                data4.shift();

                if (rdata[i].label == "normal") {
                    data3.push({
                        name: timeStr,
                        value: [
                            theTime,
                            1
                        ]
                    });
                    data4.push({
                        name: timeStr,
                        value: [
                            theTime,
                            '-'
                        ]
                    });
                } else {
                    data3.push({
                        name: timeStr,
                        value: [
                            theTime,
                            '-'
                        ]
                    });
                    data4.push({
                        name: timeStr,
                        value: [
                            theTime,
                            1
                        ]
                    });
                }

            }
            myChart.setOption({
                series: [{
                    data: data
                }, {
                    data: data2
                }, {
                    data: data3
                }, {
                    data: data4
                }]
            });

        });
    }, 3000);

});
