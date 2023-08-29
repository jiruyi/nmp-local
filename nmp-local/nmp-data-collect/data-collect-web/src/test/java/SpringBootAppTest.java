import com.matrictime.network.DataCollectApplication;
import com.matrictime.network.netty.client.NettyClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @param
 * @author jiruyi
 * @title
 * @return
 * @description
 * @create 2021/9/14 0014 15:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCollectApplication.class)
@Slf4j
public class SpringBootAppTest {

    @Autowired
    private NettyClient  nettyClient;



    @Test
    public void testRedis_1() {
//        MessageBase.Message message = new MessageBase.Message()
//                .toBuilder().setVersion(ByteString.copyFromUtf8("1")).setResv(1)
//                .setUMsgType(0).setUTotalLen(100).setDstRID(5894).setSrcRID(7822).setUIndex(0).setUEncType(0)
//                .setUEncRate(0).setCheckSum(0).setReqData("hello").build();
        Byte[] bytes = new Byte[52];
        /**
         bytes version = 1;
         bytes resv = 34;
         bytes uMsgType = 999;
         int32 uTotalLen = 980;
         bytes dstRID = 121;
         bytes srcRID = 234;
         int32 uIndex = 112;
         bytes uEncType = 111;
         bytes uEncRate = 32;
         bytes checkSum = 12;
         string reqData = 123;
          */

        ByteBuffer byteBuffer = ByteBuffer.allocate(52);
        byteBuffer.put(convertByte((byte)1));
        byteBuffer.put(convertByte((byte)1));


        byteBuffer.put(convertShort((short) 999));

        byteBuffer.put(convertInt(52));
        byteBuffer.put(convertStr("1234567891234567"));
        byteBuffer.put(convertStr("1234567891234567"));

        byteBuffer.put(convertInt(0));
        byteBuffer.put(convertByte((byte)1));
        byteBuffer.put(convertByte((byte)1));
        byteBuffer.put(convertByte((byte)1));
        byteBuffer.put(convertStr("hello"));
        //byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        nettyClient.sendMsg(byteBuffer.array());
        log.info("send msg end");

    }


    public static ByteBuffer byteslittleEndian(byte[] bytes){
        if (bytes == null || bytes.length < 0)
            return null;

        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);

        //System.out.println(Arrays.toString(convert(byteBuffer, ByteOrder.BIG_ENDIAN)));

        ByteBuffer byteBuffer2 = ByteBuffer.allocate(4);

        //System.out.println(Arrays.toString(convert(byteBuffer2, ByteOrder.LITTLE_ENDIAN)));

//        System.out.println(Arrays.toString(intToBytes(52)));
//        System.out.println(Arrays.toString(intToBytes2(52)));


//        ByteBuffer byteBuffer8 = ByteBuffer.allocate(4);
//        byteBuffer8.order(ByteOrder.BIG_ENDIAN);
//        byteBuffer8.putInt(88);
//        byte[] result1 = byteBuffer8.array();
//        System.out.println(Arrays.toString(result1));
//
//        ByteBuffer byteBuffer9 = ByteBuffer.allocate(4);
//        byteBuffer9.order(ByteOrder.LITTLE_ENDIAN);
//        byteBuffer9.putInt(88);
//        byte[] result2= byteBuffer9.array();
//        System.out.println(Arrays.toString(result2));
    }

    public static byte[] convertByte(Byte a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(a);
        return byteBuffer.array();
    }
    public static byte[] convertShort(Short a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort(a);
        return byteBuffer.array();
    }

    public static byte[] convertInt(int a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(a);
        return byteBuffer.array();
    }

    public static byte[] convertStr(String a) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(a.getBytes(StandardCharsets.UTF_8).length);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(a.getBytes(StandardCharsets.UTF_8));
        return byteBuffer.array();
    }

    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

}
