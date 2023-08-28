package com.matrictime.network.netty.protocol.message;

import lombok.Data;
import static com.matrictime.network.netty.protocol.message.Command.HEARTBEAT_REQUEST;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/22 0022 14:22
 * @desc
 */
@Data
public class HeartbeatRequestPacket  extends Packet {

    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
