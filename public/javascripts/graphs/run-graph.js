$(document).ready(function () {

    var chartliexample21 = echarts.init(document.getElementById('chartli1'));
    option = {
        backgroundColor: "#A4243B",
        color: ['#D8973C', '#FFFFFF', '#273E47'],

        title: [{
            text: 'Enquiries vs Conversions',
            left: '0.7%',
            top: '0.1%',
            textStyle: {
                color: '#ffd285'
            }
        }, {
            text: 'Transaction Analysis',
            left: '83%',
            top: '10%',
            textAlign: 'center',
            textStyle: {
                color: '#ffd285'
            }
        }],
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            x: 200,
            left: 'center',
            top: '7%',
            textStyle: {
                color: '#ffd285',
            },
            data: ['Web Design', 'SSL', 'Software Development', 'Mobile Apps', 'Domains and Hosting',
                'Email Services', 'DMT', 'IMA', 'Photography', 'Video Production', '"Studio Services',
                'Graphic Design',
                'Bulk SMS', 'SC', 'USSD', 'RBT']
        },
        grid: {
            left: '1%',
            right: '30%',
            top: '16%',
            bottom: '6%',
            containLabel: true
        },
        toolbox: {
            "show": true,
            color: '#ffffff',
            feature: {
                mark: {show: false},
                dataView: {show: false, readOnly: false},
                magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                restore: {show: true, title: 'Refresh'},
                saveAsImage: {show: true, title: 'Save As Image'},
                top: '20%'

            }
        },
        xAxis: {
            type: 'category',
            "axisLine": {
                lineStyle: {
                    color: '#c0576d'
                }
            },
            "axisTick": {
                "show": false
            },
            axisLabel: {
                textStyle: {
                    color: '#ffd285'
                }
            },
            boundaryGap: false,
            data: ['Enquiries', 'Conversions', 'Retained Clients']
        },
        yAxis: {
            "axisLine": {
                lineStyle: {
                    color: '#c0576d'
                }
            },
            splitLine: {
                show: true,
                lineStyle: {
                    color: '#c0576d'
                }
            },
            "axisTick": {
                "show": false
            },
            axisLabel: {
                textStyle: {
                    color: '#ffd285'
                }
            },
            type: 'value'
        },
        series: [{
            name: 'Web Design',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [90, 50, 39, 50, 120, 82, 80]
        }, {
            name: 'SSL',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [70, 162, 50, 87, 90, 147, 60]
        }, {
            name: 'Software Development',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Domains and Hosting',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Email Services',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Mobile Apps',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'DMT',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'IMA',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Photography',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Video Production',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Studio Services',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Graphic Design',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'Bulk SMS',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'SC',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'USSD',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        }, {
            name: 'RBT',
            smooth: true,
            type: 'line',
            symbolSize: 8,
            symbol: 'circle',
            data: [290, 335, 80, 132, 187, 330, 39]
        },
            {
                type: 'pie',
                center: ['83%', '33%'],
                radius: ['25%', '30%'],
                label: {
                    normal: {
                        position: 'center'
                    }
                },
                data: [{
                    value: 335,
                    name: 'Analysis of Transaction Sources',
                    itemStyle: {
                        normal: {
                            color: '#ffd285'
                        }
                    },
                    label: {
                        normal: {
                            formatter: '{d} %',
                            textStyle: {
                                color: '#ffd285',
                                fontSize: 20

                            }
                        }
                    }
                }, {
                    value: 310,
                    name: 'Retained Clients',
                    tooltip: {
                        show: false
                    },
                    itemStyle: {
                        normal: {
                            color: '#b04459'
                        }
                    },
                    label: {
                        normal: {
                            textStyle: {
                                color: '#ffd285',
                            },
                            formatter: '{b}',
                        }
                    }
                }]
            },

            {
                type: 'pie',
                center: ['83%', '72%'],
                radius: ['25%', '30%'],
                label: {
                    normal: {
                        position: 'center'
                    }
                },
                data: [{
                    value: 535,
                    name: 'Enquiries',
                    itemStyle: {
                        normal: {
                            color: '#ff733f'
                        }
                    },
                    label: {
                        normal: {
                            formatter: '{d} %',
                            textStyle: {
                                color: '#ff733f',
                                fontSize: 20

                            },
                        }
                    }
                }, {
                    value: 210,
                    name: 'Conversions',
                    tooltip: {
                        show: true
                    },
                    itemStyle: {
                        normal: {
                            color: '#b04459'

                        }
                    },
                    label: {
                        normal: {
                            textStyle: {
                                color: '#ffd285',
                            },
                            formatter: '{b}',
                        }
                    }
                }]
            }]
    }
    chartliexample21.setOption(option);

});