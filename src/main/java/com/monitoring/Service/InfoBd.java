package com.monitoring.Service;

import com.monitoring.model.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InfoBd {
    final private JdbcTemplate template;

    public InfoBd(JdbcTemplate template) {
        this.template = template;
    }

    public int create(int ip, Info info) {
        try {
            long cpu_busy = info.getCpuInfo().getBusy();
            long cpu_work = info.getCpuInfo().getWork();
            long mem_free = info.getMemInfo().getFree();
            long mem_total = info.getMemInfo().getTotal();
            long net_receive = info.getNetInfo().getreceive();
            long net_transmit = info.getNetInfo().gettransmit();
            long time = info.getTime();
            long rps = info.getRps();
            String sql = "INSERT INTO `Info` (`ip`,`cpu_busy`,`cpu_work`,`mem_free`,`mem_total`,`net_receive`,`net_transmit`,`time`,`rps`) VALUES (?, ?, ?, ?, ?, ?, ?, ? , ?);";
            template.update(sql, ip, cpu_busy, cpu_work, mem_free, mem_total, net_receive, net_transmit, time, rps);
            return 0;
        } catch (DuplicateKeyException dk) {
            return -1;
        }
    }

    public int checkIp(int ip){
        String sql = "SELECT `ip` " +
                "FROM `NameInfo`" +
                "WHERE `ip` = ?";
        List<Integer> result = template.queryForList(sql,Integer.class,ip);
        return result.size();
    }

    public int addLook(NameInfo nameInfo){
        int ip = nameInfo.getIp();
        String name = nameInfo.getName();
        try {
            String sql;
            if (checkIp(ip)==0) {
                sql = "INSERT INTO `NameInfo` (`name`,`ip`) VALUES (?, ?);";
            }
            else {
                sql = "UPDATE `NameInfo` SET `name` = ? WHERE `ip` = ? ;";
            }
            template.update(sql, name, ip);
            return 0;
        } catch (DuplicateKeyException dk) {
            return -1;
        }
    }

    public List<Info> getList(int limit){
        String sql = "SELECT `cpu_busy`, " +
                            "`cpu_work`, " +
                            "`mem_free`, " +
                            "`mem_total`, " +
                            "`net_receive`, " +
                            "`net_transmit`, " +
                            "`time`, " +
                            "`rps`," +
                            "`ip` " +
                     "FROM `Info` " +
                     "ORDER BY `time` DESC " +
                     "LIMIT " + limit +";" ;
        List<Info> result = template.query(sql,INFO_MAPPER);
        return result;
    }

    public List<NameInfo> getIp(){
        String sql = "SELECT `ip`, `name` FROM `NameInfo`";
        List<NameInfo> result = template.query(sql,NAME_MAPPER);
        return result;
    }

    public void allClear(){
        template.execute("SET FOREIGN_KEY_CHECKS = 0");
        template.execute("TRUNCATE TABLE `Info`;");
        template.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    final RowMapper<Info> INFO_MAPPER = (rs, rowNum) ->
            new Info(
                    new CPUInfo(rs.getLong("cpu_busy"), rs.getLong("cpu_work")),
                    new MemInfo(rs.getLong("mem_free"), rs.getLong("mem_total")),
                    new NetInfo(rs.getLong("net_receive"), rs.getLong("net_transmit")),
                    rs.getLong("rps"),
                    rs.getLong("time"),
                    rs.getInt("ip")
            );

    final RowMapper<NameInfo> NAME_MAPPER = (rs, rowNum)->
            new NameInfo(rs.getInt("ip"),rs.getString("name"));
}


