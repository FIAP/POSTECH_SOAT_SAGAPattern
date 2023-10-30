package br.com.fiapstore.cobranca.infra.messaging;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;

@Service
public class ConverterFromMessage implements IConverterMessage {

    @Autowired
    private Gson gson;
    @Override
    public HashMap<String, String>  translate(String message) {
        Type mapType = new TypeToken<HashMap<String, String>>(){}.getType();
        return new Gson().fromJson(message, mapType);
    }

}
