//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package xjtlu.cpt111.assignment.quiz.config;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XmlInputStream extends FilterInputStream {
    private static final int MIN_LENGTH = 2;
    private final StringBuilder red = new StringBuilder();
    private final StringBuilder pushBack = new StringBuilder();
    private int given = 0;
    private int pulled = 0;

    public XmlInputStream(InputStream in) {
        super(in);
    }

    public int length() {
        try {
            StringBuilder s = this.read(2);
            this.pushBack.append(s);
            return s.length();
        } catch (IOException var2) {
            return 0;
        }
    }

    private StringBuilder read(int n) throws IOException {
        boolean eof = false;
        StringBuilder s = new StringBuilder(n);

        while(s.length() < n && !eof) {
            if (this.pushBack.length() == 0) {
                eof = this.readIntoPushBack();
            }

            if (this.pushBack.length() > 0) {
                s.append(this.pushBack.charAt(0));
                this.pushBack.deleteCharAt(0);
            }
        }

        return s;
    }

    private boolean readIntoPushBack() throws IOException {
        boolean eof = false;
        int ch = this.in.read();
        if (ch >= 0) {
            if (this.pulled != 0 || !this.isWhiteSpace(ch)) {
                ++this.pulled;
                if (ch == 38) {
                    this.readAmpersand();
                } else {
                    this.pushBack.append((char)ch);
                }
            }
        } else {
            eof = true;
        }

        return eof;
    }

    private void readAmpersand() throws IOException {
        StringBuilder reference = new StringBuilder();

        int ch;
        for(ch = this.in.read(); this.isAlphaNumeric(ch); ch = this.in.read()) {
            reference.append((char)ch);
        }

        if (ch == 59) {
            String code = xjtlu.cpt111.assignment.quiz.config.XmlEntity.fromNamedReference(reference);
            if (code != null) {
                this.pushBack.append(code);
            } else {
                this.pushBack.append("&#38;").append(reference).append((char)ch);
            }
        } else {
            this.pushBack.append("&#38;").append(reference).append((char)ch);
        }

    }

    private void given(CharSequence s, int wanted, int got) {
        this.red.append(s);
        this.given += got;
    }

    public int read() throws IOException {
        StringBuilder s = this.read(1);
        this.given(s, 1, 1);
        return s.length() > 0 ? s.charAt(0) : -1;
    }

    public int read(byte[] data, int offset, int length) throws IOException {
        StringBuilder s = this.read(length);
        int n = 0;

        for(int i = 0; i < Math.min(length, s.length()); ++i) {
            data[offset + i] = (byte)s.charAt(i);
            ++n;
        }

        this.given(s, length, n);
        return n > 0 ? n : -1;
    }

    public String toString() {
        String s = this.red.toString();
        StringBuilder h = new StringBuilder();
        if (s.length() < 8) {
            for(int i = 0; i < s.length(); ++i) {
                h.append(" ").append(Integer.toHexString(s.charAt(i)));
            }
        }

        int var10000 = this.given;
        return "[" + var10000 + "]-\"" + s + "\"" + (h.length() > 0 ? " (" + String.valueOf(h) + ")" : "");
    }

    private boolean isWhiteSpace(int ch) {
        switch (ch) {
            case 9:
            case 10:
            case 13:
            case 32:
                return true;
            default:
                return false;
        }
    }

    private boolean isAlphaNumeric(int ch) {
        return 97 <= ch && ch <= 122 || 65 <= ch && ch <= 90 || 48 <= ch && ch <= 57;
    }
}
