package advent.of.code.day23;


class Elf {
    //private isEmpty; TODO:
    private Coordinate oldLoc;
    private int proposalDir;

    /*Elf(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }*/

    Elf(Coordinate loc) {
        oldLoc = loc;
        proposalDir = -1;
    }

    Coordinate getOldLoc() {
        return oldLoc;
    }

    int getProposalDir() {
        return proposalDir;
    }

    void setOldLoc(Coordinate newLoc) {
        oldLoc = newLoc;
    }

    void setProposalDir(int newProposal) {
        proposalDir = newProposal;
    }

}