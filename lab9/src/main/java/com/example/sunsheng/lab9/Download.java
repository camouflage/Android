package com.example.sunsheng.lab9;

import android.os.Message;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by sunsheng on 18/12/15.
 */
public class Download implements Runnable {
    private final String NAMESPACE = "http://WebXml.com.cn/";
    private final String METHODNAME = "enValidateByte";
    private final String SOAPACTION = "http://WebXml.com.cn/enValidateByte";
    private final String URL= "http://webservice.webxml.com.cn/WebServices/ValidateCodeWebService.asmx";

    private static final int UPDATE_CONTENT = 0;

    @Override
    public void run() {
        SoapObject request = new SoapObject(NAMESPACE, METHODNAME);
        request.addProperty("byString", Create.code);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE transportSE = new HttpTransportSE(URL);

        try {
            transportSE.call(SOAPACTION, envelope);
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        Log.e("fault", envelope.bodyIn.toString());
        SoapObject result = (SoapObject) envelope.bodyIn;
        SoapPrimitive detail = (SoapPrimitive) result.getProperty("enValidateByteResult");

        Message message = new Message();
        message.what = UPDATE_CONTENT;
        message.obj = detail;

        Create.handler.sendMessage(message);
        Create.progressDialog.dismiss();
    }
}
