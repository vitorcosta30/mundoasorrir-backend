package com.mundoasorrir.mundoasorrirbackend.Message;

public class ErrorMessage {
    public static final ResponseMessage ATTENDANCE_NOT_INSTANTIETED = new ResponseMessage("Houve um erro: presenças não instanciadas!");

    public static final ResponseMessage INVALID_DATE = new ResponseMessage("Erro: data não é valida!!");
    public static final ResponseMessage ERROR = new ResponseMessage("Aconteceu um erro!!");

    public static final ResponseMessage NOT_ALLOWED = new ResponseMessage("Não tem acesso a esta funcionalidade!!");

}
