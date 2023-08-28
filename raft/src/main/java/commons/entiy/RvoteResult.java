package commons.entiy;

public class RvoteResult {
    long term;
    boolean voteGranted;

    public RvoteResult(boolean voteGranted) {
        this.voteGranted = voteGranted;
    }

    private RvoteResult(Builder builder) {
        this.term = builder.term;
        this.voteGranted = builder.voteGranted;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static RvoteResult fail() {
        return new RvoteResult(false);
    }


    public static final class Builder {
        long term;
        boolean voteGranted;
        private Builder() {}

        public Builder setTerm(long term) {
            this.term = term;
            return this;
        }

        public Builder setVoteGranted(boolean voteGranted) {
            this.voteGranted = voteGranted;
            return this;
        }

        public RvoteResult build(){
            return new RvoteResult(this);
        }
    }
}
