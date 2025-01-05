package aliveandwell.aliveandwell.accessor;

public interface SPlayerAccessorNbt {
    boolean splayerNbt$contains(String key);

    long splayerNbt$getLong(String key);
    void splayerNbt$putLong(String key, long value);

    void splayerNbt$removeString(String key);

    void splayerNbt$invulnerableFlag(boolean fllag);
}
