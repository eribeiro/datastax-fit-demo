package br.eribeiro.dse.search;

import example.avro.User;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class AvroCodec
{
    private AvroCodec(){}
    
    public static String encodeAvroObjectAsHex(User user) throws IOException
    {
        // encode user object to byte-array then hexadecimal string
        byte[] data = encodeToByteArray(user);
        System.out.println("byte array length: " + data.length);
        String base64 = Hex.encodeHexString(data);
        System.out.println("string length:" + base64.length());
        return base64;
    }

    public static User decodeHexStringAsAvroObject(String hexString) throws DecoderException, IOException
    {
        // decode from hex string to byte-array then to user object
        byte[] data = Hex.decodeHex(hexString.toCharArray());
        User user = decodeFromByteArray(data);
        return user;
    }


    public static byte[] encodeToByteArray(User user) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        DatumWriter<User> writer = new SpecificDatumWriter<User>(User.getClassSchema());

        writer.write(user, encoder);
        encoder.flush();
        out.close();
        return out.toByteArray();
    }

    public static User decodeFromByteArray(byte[] value) throws IOException
    {
        SpecificDatumReader<User> reader = new SpecificDatumReader<User>(User.getClassSchema());
        Decoder decoder = DecoderFactory.get().binaryDecoder(value, null);
        return reader.read(null, decoder);
    }
}
