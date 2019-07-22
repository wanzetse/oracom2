$(document).ready(function () {
    var chartliexample1 = echarts.init(document.getElementById('pieChart1'));

    option = {
        backgroundColor: '#97F9F9',

        title: {
            text: 'Pie Chart Example',
            subtext: '',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br />{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['Public Water Supply', 'Hydro Electricity', 'Flood Control', 'Irrigation', 'Others'],

        },
        toolbox: {
            show: true,
            feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                magicType: {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                        funnel: {
                            x: '25%',
                            width: '50%',
                            funnelAlign: 'left',
                            max: 1548
                        }
                    }
                },
                restore: { show: true },
                saveAsImage: { show: true }
            }
        },
        color: ["#EF476F", "#FFD166", "#06D6A0", "#118AB2", "#073B4C"],
        calculable: true,
        series: [
            {
                name: 'Access Sources',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    { value: 335, name: 'Public Water Supply' },
                    { value: 310, name: 'Hydro Electricity' },
                    { value: 234, name: 'Flood Control' },
                    { value: 135, name: 'Irrigation' },
                    { value: 1548, name: 'Others' }
                ],
                label: {
                    normal: {
                        formatter: "{b} : {c} ({d}%)"
                    }
                }
            }
        ]
    };

    chartliexample1.setOption(option);
});