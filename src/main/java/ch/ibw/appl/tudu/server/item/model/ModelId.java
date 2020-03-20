package ch.ibw.appl.tudu.server.item.model;

public class ModelId {
    public Long id;

    public static ModelId create(Long id) {
        ModelId model = new ModelId();
        model.id = id;
        return model;
    }
}
