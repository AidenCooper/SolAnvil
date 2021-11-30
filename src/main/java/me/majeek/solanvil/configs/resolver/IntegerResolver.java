package me.majeek.solanvil.configs.resolver;

public class IntegerResolver implements Resolver<Integer> {
    @Override
    public Integer resolve(Object value) {
        return Integer.valueOf(value.toString());
    }
}
