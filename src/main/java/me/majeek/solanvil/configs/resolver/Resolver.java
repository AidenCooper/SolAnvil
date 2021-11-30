package me.majeek.solanvil.configs.resolver;

public interface Resolver<T> {
    T resolve(Object value);
}
