package timeserverwithpojo;

import java.util.Date;

/**
 * @author zonzie
 * @date 2018/4/8 19:25
 */
public class UnixTIme {
    private final long value;

    public UnixTIme() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnixTIme(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}
