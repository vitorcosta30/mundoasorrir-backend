package com.mundoasorrir.mundoasorrirbackend.Message;

public class SuccessMessage {
    public static final ResponseMessage MARKED_PRESENCE_SUCCESS = new ResponseMessage("Presença marcada com sucesso!");
    public static final ResponseMessage MARKED_ABSENCE_SUCCESS = new ResponseMessage("Falta marcada com sucesso!");

    public static final ResponseMessage SIGNED_OUT = new ResponseMessage("Terminou a sessão!!");
    public static final ResponseMessage EVENT_CREATED = new ResponseMessage("Evento criado com sucesso!!");
    public static final ResponseMessage FILE_UPLOADED= new ResponseMessage("Ficheiro enviado com sucesso!!");

    public static final ResponseMessage DATA_WAS_UPDATED = new ResponseMessage("Dados atualizados com sucesso!!");

    public static final ResponseMessage PASSWORD_WAS_UPDATED = new ResponseMessage("Password foi atualizada com sucesso!!");

    public static final ResponseMessage USER_CREATED = new ResponseMessage("Utilizador criado com sucesso!!");

    public static final ResponseMessage TOKEN_REFRESHED = new ResponseMessage("Token foi atualizado!!");

    public static final ResponseMessage VACATION_ACCEPTED = new ResponseMessage("Pedido de férias foi aceite com sucesso!!");


    public static final ResponseMessage VACATION_REJECTED = new ResponseMessage("Pedido de férias foi rejeitado com sucesso!!");
    public static final ResponseMessage VACATION_REQUEST_CREATED = new ResponseMessage("Pedido de férias foi criado com sucesso!!");
    public static final ResponseMessage ACCOUNT_DEACTIVATED = new ResponseMessage("Conta desativada com sucesso!!");
    public static final ResponseMessage ACCOUNT_ACTIVATED = new ResponseMessage("Conta ativada com sucesso!!");
    public static final ResponseMessage PROJECT_DEACTIVATED = new ResponseMessage("Projecto desativado com sucesso!!");
    public static final ResponseMessage PROJECT_ACTIVATED = new ResponseMessage("Projecto ativado com sucesso!!");
    public static final ResponseMessage PROJECT_CREATED = new ResponseMessage("Projecto criado com sucesso!!");

    public static final ResponseMessage USER_ADDED = new ResponseMessage("Utilizador adicionado com sucesso!!");
    public static final ResponseMessage USER_REMOVED = new ResponseMessage("Utilizador removido com sucesso!!");

    public static final ResponseMessage LEFT_GROUP = new ResponseMessage("Saiu do grupo com sucesso!!");

}
