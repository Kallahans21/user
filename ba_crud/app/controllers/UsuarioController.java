package controllers;

import play.mvc.*;
import javax.inject.Inject;
import java.util.List;
import play.data.FormFactory;
import play.data.Form;

import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.create;

import javax.inject.Inject;
import java.util.List;

public class UsuarioController extends Controller {

    private final FormFactory formFactory;
    private final MessagesApi messagesApi;

public class UsuarioController extends Controller {

    private final FormFactory formFactory;
    @Inject
    public UsuarioController(FormFactory formFactory) {
        this.formFactory = formFactory;
        this.messagesApi = messagesApi;

    }


    public Result index() {
        List<UsuarioEntity> usuarios = db.withConnection(connection -> {
            List<UsuarioEntity> userList = connection.createQuery("SELECT * FROM usuario")
                    .executeQuery()
                    .map(row -> new UsuarioEntity(
                            row.getLong("id"),
                            row.getString("nombre"),
                            row.getDate("fecha_nacimiento"),
                            row.getBoolean("activo")
                    ))
                    .list();
            return userList;
        });
        return ok(index.render(usuarios));
    }

    public Result create() {
        Form<UsuarioEntity> usuarioForm = formFactory.form(UsuarioEntity.class);
        return ok(create.render(usuarioForm));
    }

    public Result save() {
        Form<UsuarioEntity> usuarioForm = formFactory.form(UsuarioEntity.class).bindFromRequest();
        if (usuarioForm.hasErrors()) {
            return badRequest(create.render(usuarioForm));
        }
        UsuarioEntity usuario = usuarioForm.get();

        db.withConnection(connection -> {
            connection.createStatement().executeUpdate(
                    "INSERT INTO usuario (nombre, fecha_nacimiento, activo) VALUES (?, ?, ?)",
                    usuario.getNombre(),
                    usuario.getFechaNacimiento(),
                    usuario.isActivo()
            );
        });

        return redirect(routes.UsuarioController.index());
    }

    public Result edit(Long id) {
        UsuarioEntity usuario = db.withConnection(connection -> {
            return connection.createQuery("SELECT * FROM usuario WHERE id = :id")
                    .addParameter("id", id)
                    .executeQuery()
                    .map(row -> new UsuarioEntity(
                            row.getLong("id"),
                            row.getString("nombre"),
                            row.getDate("fecha_nacimiento"),
                            row.getBoolean("activo")
                    ))
                    .first();
        });

        if (usuario == null) {
            return notFound("Usuario no encontrado");
        }

        Form<UsuarioEntity> usuarioForm = formFactory.form(UsuarioEntity.class).fill(usuario);
        return ok(edit.render(id, usuarioForm));
    }

    public Result update(Long id) {
        Form<UsuarioEntity> usuarioForm = formFactory.form(UsuarioEntity.class).bindFromRequest();
        if (usuarioForm.hasErrors()) {
            return badRequest(edit.render(id, usuarioForm));
        }
        UsuarioEntity usuario = usuarioForm.get();

        db.withConnection(connection -> {
            connection.createStatement().executeUpdate(
                    "UPDATE usuario SET nombre = ?, fecha_nacimiento = ?, activo = ? WHERE id = ?",
                    usuario.getNombre(),
                    usuario.getFechaNacimiento(),
                    usuario.isActivo(),
                    id
            );
        });

        return redirect(routes.UsuarioController.index());
    }

    public Result delete(Long id) {
        db.withConnection(connection -> {
            connection.createStatement().executeUpdate("DELETE FROM usuario WHERE id = ?", id);
        });
        return redirect(routes.UsuarioController.index());
    }
}
