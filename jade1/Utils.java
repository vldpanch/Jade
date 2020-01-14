package jade1;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

class Utils {

    static String serializeToString(Serializable o) {
        try (ByteArrayOutputStream byte_out = new ByteArrayOutputStream();
             ObjectOutputStream obj_out = new ObjectOutputStream(byte_out)) {

            obj_out.writeObject(o);
            return Base64.getEncoder().encodeToString(byte_out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

     static Object deserializeFromString(String s) {
        try (ObjectInputStream obj_in = new ObjectInputStream(
                new ByteArrayInputStream(Base64.getDecoder().decode(s)))) {

            return obj_in.readObject();

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    static String mapToString(HashMap<String, Integer> map) {
        return Arrays.toString(map.entrySet().toArray());
    }
}
