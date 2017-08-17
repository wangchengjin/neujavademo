/**
 * Created by jizhong on 2017/8/16.
 */

var myChart = echarts.init(document.getElementById('mainChart'));
myChart.showLoading();
$.get('/windDataJson').done(function (mapData) {
    var legends = ['正常数据', '训练异常数据','训练拟合曲线', '训练拟合曲线上界', '训练拟合曲线下界'];

    var data = mapData.resultData;
    var myRegressionPoints = [];
    var myRegressionDownPoints = [];
    var myRegressionUpPoints = [];
    for (var i = 0; i < data.length; i++) {
        myRegressionPoints.push([data[i].windSpeed, data[i].validPower]);
        myRegressionDownPoints.push([data[i].windSpeed, data[i].lineDown]);
        myRegressionUpPoints.push([data[i].windSpeed, data[i].lineUp]);
    }
    myRegressionPoints.sort(function (a, b) {
        return a[0] - b[0];
    });

    myRegressionDownPoints.sort(function (a, b) {
        return a[0] - b[0];
    });

    myRegressionUpPoints.sort(function (a, b) {
        return a[0] - b[0];
    });

    var nomalData = mapData.nomalData;
    var exceptionData = mapData.exceptionData;

    var scatterNomal = [];
    var scatterException = [];
    for (var i = 0; i < nomalData.length; i++) {
        scatterNomal.push([nomalData[i].windSpeed, nomalData[i].powerValid]);
    }
    for (var i = 0; i < exceptionData.length; i++) {
        scatterException.push([exceptionData[i].windSpeed, exceptionData[i].powerValid]);
    }
    var series = [{
        name: legends[0],
        type: 'scatter',
        //large:true,
        symbolSize: 3,
        data: scatterNomal
    }, {
        name: legends[1],
        type: 'scatter',
        //large:true,
        symbolSize: 3,
        data: scatterException
    }, {
            name: legends[2],
            type: 'line',
            data: myRegressionPoints
    }, {
            name: legends[3],
            type: 'line',
            data: myRegressionUpPoints
    }, {
            name: legends[4],
            type: 'line',
            data: myRegressionDownPoints
    }];
    myChart.hideLoading();
    myChart.setOption({
        title: {
            text: '风电数据分析示例'
        },
        tooltip: {},
        legend: {
            data: legends
        },
        xAxis: {
            name: '一秒平均风速[m/s]',
            nameLocation: 'middle',
            nameGap: 35,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            type: 'value',
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        yAxis: {
            name: '有功功率[kW]',
            nameGap: 50,
            min: -5,
            nameTextStyle: {fontWeight: 'lighter', fontSize: 13},
            nameLocation: 'middle',
            type: 'value',
            splitLine: {
                lineStyle: {
                    type: 'dashed'
                }
            }
        },
        series: series
    });
});