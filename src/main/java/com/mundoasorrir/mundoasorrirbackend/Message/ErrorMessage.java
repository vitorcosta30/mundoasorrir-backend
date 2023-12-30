package com.mundoasorrir.mundoasorrirbackend.Message;

public class ErrorMessage {
    public static final ResponseMessage ATTENDANCE_NOT_INSTANTIETED = new ResponseMessage("Houve um erro: presenças não instanciadas!");

    public static final ResponseMessage INVALID_DATE = new ResponseMessage("Erro: data não é valida!!");
    public static final ResponseMessage ERROR = new ResponseMessage("Aconteceu um erro!!");
    public static final ResponseMessage NOT_ALLOWED = new ResponseMessage("Não tem acesso a esta funcionalidade!!");




    public static final ResponseMessage ACCOUNT_DEACTIVATED = new ResponseMessage("Conta desativada, entre em contacto com alguem responsavel caso se trate de um erro");
    public static final ResponseMessage EMAIL_IN_USE = new ResponseMessage("Erro: E-mail já se encontra em uso");
    public static final ResponseMessage USERNAME_IN_USE = new ResponseMessage("Erro: username já se encontra em uso");

    public static final ResponseMessage GROUPNAME_IN_USE = new ResponseMessage("Erro: Nome de grupo já se encontra em uso");

    public static final ResponseMessage ROLE_DOES_NOT_EXIST = new ResponseMessage("Erro: cargo não existe");

    public static final ResponseMessage ILLEGAL_REQUEST = new ResponseMessage("Existe algo de errado no pedido!!");
    public static final ResponseMessage WRONG_PASSWORD= new ResponseMessage("A password está errada!!");
    public static final ResponseMessage PASSWORDS_DONT_MATCH= new ResponseMessage("A nova password e repetição não são iguais!!");

    public static final ResponseMessage TOKEN_EMPTY= new ResponseMessage("Token está vazio!!");

    public static final ResponseMessage EVENT_TYPE_NULL= new ResponseMessage("Tipo de evento está vazio!!");
    public static final ResponseMessage EVENT_TYPE_NOT_EXIST= new ResponseMessage("Tipo de evento não existe!!");
    public static final ResponseMessage FILE_UPLOAD_FAILED= new ResponseMessage("Upload de ficheiro falhou!!");
    public static final ResponseMessage VACATION_REJECT_FAILED= new ResponseMessage("Pedido para rejeitar pedido de férias falhou!!");
    public static final ResponseMessage VACATION_ACCEPT_FAILED= new ResponseMessage("Pedido para aceitar pedido de férias falhou!!");
    public static final ResponseMessage ACCOUNT_DEACTIVATION_FAILED= new ResponseMessage("Desativação da conta falhou!!");


    public static final ResponseMessage ACCOUNT_ACTIVATION_FAILED= new ResponseMessage("Ativação da conta falhou!!");





}
