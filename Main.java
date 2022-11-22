package org.example;


public class Main {
    public static void main(String[] args) {

        INFINIDAT myInfinidat = new INFINIDAT();

        // Show volumes
        myInfinidat.showVols("volumes?type=eq:MASTER&pool_name=eq:EMEA-POOL");

        // Show snapshots for volume id 280
        myInfinidat.showVols("volumes?type=eq:SNAPSHOT&parent_id=eq:280");

        // Get pool id given a pool name
        int poolId = myInfinidat.getPoolId("EMEA-Pool");

    }
}