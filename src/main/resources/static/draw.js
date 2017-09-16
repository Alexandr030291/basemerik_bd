/**
 * Created by alexandr on 16.09.17.
 */

(function () {
    let sizeToString = function (bytes) {
        // One way to write it, not the prettiest way to write it.

        let fmt = d3.format('.0f');
        if (bytes < 1000) {
            return fmt(bytes) + '1/s';
        } else if (bytes < 1000 * 1000) {
            return fmt(bytes / 1000) + 'k/s';
        } else if (bytes < 1000 * 1000 * 1000) {
            return fmt(bytes / 1000 / 1000) + 'M/s';
        } else {
            return fmt(bytes / 1000 / 1000 / 1000) + 'G/s';
        }
    };


    let bytesToString = function (bytes) {
        // One way to write it, not the prettiest way to write it.

        let fmt = d3.format('.0f');
        if (bytes < 1024) {
            return fmt(bytes) + 'B/s';
        } else if (bytes < 1024 * 1024) {
            return fmt(bytes / 1024) + 'kB/s';
        } else if (bytes < 1024 * 1024 * 1024) {
            return fmt(bytes / 1024 / 1024) + 'MB/s';
        } else {
            return fmt(bytes / 1024 / 1024 / 1024) + 'GB/s';
        }
    };
    window.bytesToString=bytesToString;
    window.sizeToString=sizeToString;
})();

(function () {
   // console.log("Update 2");
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'db/api/name', false);
    xhr.send();
    let name_ip = [];
    if (xhr.status != 200) {
        // обработать ошибку
        console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
    } else {
        // вывести результат
        name_ip = JSON.parse(xhr.responseText);
    }
   // console.log(name_ip);

    let axes = {};
    let map_name = new Map();
    let map_info_cpu = new Map();
    let map_info_mem = new Map();
    let map_info_rps = new Map();
    let map_info_receive = new Map();
    let map_info_transmit  = new Map();
    let map_info_time  = new Map();
    let types = {};

    for (let i=0; i<name_ip.length; i++){
        let ip_name ="" + name_ip[i].name + " ";
        let ip_value = name_ip[i].ip;
        let ip_arr = [];
        while (ip_value>0){
           // console.log(ip_value);
            ip_arr.push(ip_value%256);
            ip_value>>=8;
        }
        ip_name += ip_arr[ip_arr.length-1];
        for (let j = ip_arr.length-2; j>= 0; j--){
            ip_name += "."+ip_arr[j] ;
        }
        let axes_time = ip_name + " t";
        map_name.set(name_ip[i].ip,ip_name);
        map_info_cpu.set(name_ip[i].ip,[ip_name]);
        map_info_mem.set(name_ip[i].ip,[ip_name]);
        map_info_rps.set(name_ip[i].ip,[ip_name]);
        map_info_receive.set(name_ip[i].ip,[ip_name]);
        map_info_transmit.set(name_ip[i].ip,[ip_name]);
        map_info_time.set(name_ip[i].ip,[axes_time]);
        types[ip_name] = 'area-spline';
        axes[ip_name] = axes_time;
    }
  //  console.log(map_name);
  //  console.log(map_info_cpu);

    let len_info_one_ip = 60;
    xhr.open('GET', 'db/api/len', false);
    xhr.send();
    if (xhr.status != 200) {
        // обработать ошибку
        console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
    } else {
        len_info_one_ip =JSON.parse(xhr.responseText);
    //    console.log(len_info_one_ip);
    }

    let len_info = len_info_one_ip * name_ip.length;
    xhr.open('GET', 'db/api/info?len='+len_info, false);
    xhr.send();
    let infos = [];
    if (xhr.status != 200) {
        // обработать ошибку
        console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
    } else {
        // вывести результат
        infos = JSON.parse(xhr.responseText);
        for (let i=0; i<infos.length; i++) {
            map_info_time.get(infos[i].ip).push(new Date(infos[i].time*1000));
            map_info_cpu.get(infos[i].ip).push(infos[i].cpuInfo.busy/infos[i].cpuInfo.work);
            map_info_mem.get(infos[i].ip).push(1-infos[i].memInfo.free/infos[i].memInfo.total);
            map_info_rps.get(infos[i].ip).push(infos[i].rps);
            map_info_receive.get(infos[i].ip).push(-infos[i].netInfo.receive);
            map_info_transmit.get(infos[i].ip).push(infos[i].netInfo.transmit);
        }
    }

    let columns_cpu = [];
    let columns_mem = [];
    let columns_rps = [];
    let columns_receive = [];
    let columns_transmit = [];

    for (let i=0; i<name_ip.length; i++) {
        columns_cpu.push(map_info_cpu.get(name_ip[i].ip));
        columns_cpu.push(map_info_time.get(name_ip[i].ip));

        columns_mem.push(map_info_mem.get(name_ip[i].ip));
        columns_mem.push(map_info_time.get(name_ip[i].ip));

        columns_rps.push(map_info_rps.get(name_ip[i].ip));
        columns_rps.push(map_info_time.get(name_ip[i].ip));


        columns_receive.push(map_info_time.get(name_ip[i].ip));
        columns_receive.push(map_info_receive.get(name_ip[i].ip));

        columns_transmit.push(map_info_transmit.get(name_ip[i].ip));
        columns_transmit.push(map_info_time.get(name_ip[i].ip));

    }

 //   console.log(axes);

 //   console.log(infos[0]);

    let axes_x ={
        label: '',
        type: 'timeseries',
        tick: {
            rotate: 90,
            count: 11,
            format: '%H:%M:%S'
        },
        fontsize: 18
    };

    let chart_cpu = c3.generate({
        bindto: '#chart_cpu',
        data: {
            xs: axes,
            columns: columns_cpu,
            types: types
        },
        grid: {
            x: {
                show: true
            },
            y: {
                show: true
            }
        },
        axis: {
            x: axes_x,
            y: {
                tick: {
                    format: d3.format("%")
                },
                max: 0.9,
            }
        },
        point: {
            show: false
        }
    });
    let chart_mem = c3.generate({
        bindto: '#chart_mem',
        data: {
            xs: axes,
            columns: columns_mem,
            types: types
        },
        grid: {
            x: {
                show: true
            },
            y: {
                show: true
            }
        },
        axis: {
            x: axes_x,
            y: {
                tick: {
                    format: d3.format("%")
                },
                max: 0.9,
            }
        },
        point: {
            show: false
        }
    });
    let chart_rpc = c3.generate({
        bindto: '#chart_rpc',
        data: {
            xs: axes,
            columns: columns_rps,
            types: types
        },
        grid: {
            x: {
                show: true
            },
            y: {
                show: true
            }
        },
        axis: {
            x: axes_x,
            y: {
                tick: {
                    format: sizeToString,
                }
            }
        },
        point: {
            show: false
        }
    });
    let chart_receive = c3.generate({
        bindto: '#chart_receive',
        data: {
            xs: axes,
            columns: columns_receive,
            types: types
        },
        grid: {
            x: {
                show: true
            },
            y: {
                show: true
            }
        },
        axis: {
            x: axes_x,
            y: {
                tick: {
                    format: bytesToString
                }
            }
        },
        point: {
            show: false
        }
    });
    let chart_transmit = c3.generate({
        bindto: '#chart_transmit',
        data: {
            xs: axes,
            columns: columns_transmit,
            types: types
        },
        grid: {
            x: {
                show: true
            },
            y: {
                show: true
            }
        },
        axis: {
            x: axes_x,
            y: {
                tick: {
                    format: bytesToString
                }
            }
        },
        point: {
            show: false
        }
    });
})();
