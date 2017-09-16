/**
 * Created by alexandr on 16.09.17.
 */
(function () {
    console.log("Update 1");
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
    console.log(name_ip);
    let name_arr = [];

    for (let i=0; i<name_ip.length; i++){
        let ip_name ="" + name_ip[i].name + "; ";
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

        name_arr.push(ip_name);
    }
    console.log(name_arr);

    const len_info_one_ip = 600;
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
    }

    console.log(infos);

    let chart_cpu = c3.generate({
        bindto: '#chart_cpu',
        data: {
            columns: [
                ['data1', 300, 350, 300, 0, 0, 0],
                ['data2', 130, 100, 140, 200, 150, 50]
            ],
            types: {
                data1: 'area-spline',
                data2: 'area-spline'
            }
        }
    });
    let chart_mem = c3.generate({
        bindto: '#chart_mem',
        data: {
            columns: [
                ['data1', 300, 350, 300, 0, 0, 0],
                ['data2', 130, 100, 140, 200, 150, 50]
            ],
            types: {
                data1: 'area-spline',
                data2: 'area-spline'
            }
        }
    });
    let chart_rpc = c3.generate({
        bindto: '#chart_rpc',
        data: {
            columns: [
                ['data1', 300, 350, 300, 0, 0, 0],
                ['data2', 130, 100, 140, 200, 150, 50]
            ],
            types: {
                data1: 'area-spline',
                data2: 'area-spline'
            }
        }
    });
    let chart_receive = c3.generate({
        bindto: '#chart_receive',
        data: {
            columns: [
                ['data1', 300, 350, 300, 0, 0, 0],
                ['data2', 130, 100, 140, 200, 150, 50]
            ],
            types: {
                data1: 'area-spline',
                data2: 'area-spline'
            }
        }
    });
    let chart_transmit = c3.generate({
        bindto: '#chart_transmit',
        data: {
            columns: [
                ['data1', 300, 350, 300, 0, 0, 0],
                ['data2', 130, 100, 140, 200, 150, 50]
            ],
            types: {
                data1: 'area-spline',
                data2: 'area-spline'
            }
        }
    });
})();
