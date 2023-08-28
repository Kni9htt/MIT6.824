package rpc.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcReponse<T> implements Serializable {


    private T result;

    public RpcReponse(T result) {
        this.result = result;
    }

    private RpcReponse(Builder builder) {
        setResult((T) builder.result);
    }

    public static RpcReponse<String> ok() {
        return new RpcReponse<>("ok");
    }

    public static RpcReponse<String> fail() {
        return new RpcReponse<>("fail");
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    @Override
    public String toString() {
        return "Response{" +
                "result=" + result +
                '}';
    }

    public static final class Builder {

        private Object result;

        private Builder() {
        }

        public Builder result(Object val) {
            result = val;
            return this;
        }

        public RpcReponse<?> build() {
            return new RpcReponse(this);
        }
    }
}