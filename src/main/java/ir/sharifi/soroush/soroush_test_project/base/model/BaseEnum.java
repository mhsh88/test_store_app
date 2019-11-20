package ir.sharifi.soroush.soroush_test_project.base.model;

import java.io.Serializable;

public interface BaseEnum<E> extends Serializable {
    E getValue();
}
