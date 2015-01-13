package br.eribeiro.dse.search;

import com.datastax.driver.core.*;
import example.avro.User;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AvroIngester
{

    private static Cluster cluster;
    private static Session session;

    public static void main(String[] args) throws IOException, DecoderException
    {
//        generateAvro();
        
        insertAndRetrieveUser();
    }
    
    //TODO: encode as HEX string, but encode to Base64 before? Or compress before?
    public static void generateAvro() throws IOException, DecoderException
    {
        // build user object
        User user1 = User.newBuilder()
                         .setName("Bruno")
                         .setAge(13)
                         .setCity("Brasilia")
                         .setCountry(null).build();
        
        String hexString = AvroCodec.encodeAvroObjectAsHex(user1);
        System.out.println(hexString);
        
        User user2 = AvroCodec.decodeHexStringAsAvroObject(hexString);
        
        System.out.println(user1.equals(user2));

    }
    
    public static void insertAndRetrieveUser() throws IOException, DecoderException
    {
        connect();

        // build user object
        User user = User.newBuilder()
                        .setName("Bruno Berto de Oliveira Ribeiro")
                        .setAge(13)
                        .setCity("Brasilia")
                        .setCountry("Brazil")
                        .build();

        for (int i = 1; i < 4; i++)
        {
            insertUser(i, user);
        }
        

        retrieveAllRows();

        closeConnection();
    }

    private static void closeConnection()
    {
        cluster.close();
    }

    private static void insertUser(int id, User user) throws IOException
    {
        // to insert a blob it should be as a hex "string"
        String hex = AvroCodec.encodeAvroObjectAsHex(user);
        String cmd = "insert into blog.post (id, post) values (" + id + ", '" + hex + "');";
        System.out.println(cmd);
        session.execute(cmd);
    }

    private static void retrieveAllRows() throws IOException, DecoderException
    {
        ResultSet rs = session.execute("select * from blog.post;");

        for (Row row : rs)
        {
            System.out.println(row.getInt("id"));

//            ByteBuffer data = row.getBytes("post");  // IF BLOB
            String hex = row.getString("post");
            
//            User user = decodeFromByteArray(data.array()); // IF BLOB
            User user = AvroCodec.decodeHexStringAsAvroObject(hex);

            System.out.println(user.toString());

        }
    }

    private static void connect()
    {
        cluster = Cluster.builder()
                .addContactPoint("127.0.0.1")
                .build();

        Metadata metadata = cluster.getMetadata();

        System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());

        session = cluster.connect();
    }

}
