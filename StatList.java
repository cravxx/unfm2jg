class StatList {

    /**
     * The Acceleration stat
     * first value being how fast the car can move from rest (not moving).
     * Second and third value meaning how much speed it gains while the car is moving
     * @author Omar Waly
     */
    public static final float[][] acelf = {
            {
                    11.0F, 5.0F, 3.0F
            }, {
            14.0F, 7.0F, 5.0F
    }, {
            10.0F, 5.0F, 3.5F
    }, {
            11.0F, 6.0F, 3.5F
    }, {
            10.0F, 5.0F, 3.5F
    }, {
            12.0F, 6.0F, 3.0F
    }, {
            7.0F, 9.0F, 4.0F
    }, {
            11.0F, 5.0F, 3.0F
    }, {
            12.0F, 7.0F, 4.0F
    }, {
            12.0F, 7.0F, 3.5F
    }, {
            11.5F, 6.5F, 3.5F
    }, {
            9.0F, 5.0F, 3.0F
    }, {
            13.0F, 7.0F, 4.5F
    }, {
            7.5F, 3.5F, 3.0F
    }, {
            11.0F, 7.5F, 4.0F
    }, {
            12.0F, 6.0F, 3.5F
    }
    };
    /**
     * The Top Speed stat
     * Take the values as if each one was a gear from a gearbox. 1st to 2nd, 2nd to 3rd, dependent on power obtained
     * @author Omar Waly
     */
    static final int[][] swits = {
            {
                    50, 185, 282
            }, {
            100, 200, 310
    }, {
            60, 180, 275
    }, {
            76, 195, 298
    }, {
            70, 170, 275
    }, {
            70, 202, 293
    }, {
            60, 170, 289
    }, {
            70, 206, 291
    }, {
            90, 210, 295
    }, {
            90, 190, 276
    }, {
            70, 200, 295
    }, {
            50, 160, 270
    }, {
            90, 200, 305
    }, {
            50, 130, 210
    }, {
            80, 200, 300
    }, {
            70, 210, 290
    }
    };
    /**
     * Handbrake power
     * Basically, the braking power. Any negative value, would make you shoot forward instead of backward
     * @author Omar Waly
     */
    static final int[] handb = {
            7, 10, 7, 15, 12, 8, 9, 10, 5, 7, 8, 10, 8, 12, 7, 7
    };
    /**
     * Aerial rotation
     * If the value is too much, your car will just rotate rapidly and you can't control it.
     * @author Omar Waly
     */
    static final float[] airs = {
            1.0F, 1.2F, 0.95F, 1.0F, 2.2F, 1.0F, 0.9F, 0.8F, 1.0F, 0.9F, 1.15F, 0.8F, 1.0F, 0.3F, 1.3F, 1.0F
    };
    /**
     * Aerial control
     * This controls how high or low you'll go if you do flips, or how far you'll go if you do rollspins.
     * This also effects the statbar of Aerial Control, but if this value is noticeably low, then the airs
     * value will be used to display how much Aerial Control a car has.
     * @author Omar Waly
     */
    static final int[] airc = {
            70, 30, 40, 40, 30, 50, 40, 90, 40, 50, 75, 10, 50, 0, 100, 60
    };
    /**
     * Turning responsiveness
     * @author Omar Waly
     */
    static final int[] turn = {
            6, 9, 5, 7, 8, 7, 5, 5, 9, 7, 7, 4, 6, 5, 7, 6
    };
    /**
     * Grip of the car to the ground
     * @author Omar Waly
     */
    static final float[] grip = {
            20.0F, 27.0F, 18.0F, 22.0F, 19.0F, 20.0F, 25.0F, 20.0F, 19.0F, 24.0F, 22.5F, 25.0F, 30.0F, 27.0F, 25.0F,
            27.0F
    };
    /**
     * How much your car bounces after landing stunts
     * @author Omar Waly
     */
    static final float[] bounce = {
            1.2F, 1.05F, 1.3F, 1.15F, 1.3F, 1.2F, 1.15F, 1.1F, 1.2F, 1.1F, 1.15F, 0.8F, 1.05F, 0.8F, 1.1F, 1.15F
    };
    /**
     * Tolerance towards track pieces
     * @author Omar Waly
     */
    static final float[] simag = {
            0.9F, 0.85F, 1.05F, 0.9F, 0.85F, 0.9F, 1.05F, 0.9F, 1.0F, 1.05F, 0.9F, 1.1F, 0.9F, 1.3F, 0.9F, 1.15F
    };
    /**
     * Car strength
     * @author Omar Waly
     */
    static final float[] moment = {
            1.3F, 0.75F, 1.4F, 1.2F, 1.1F, 1.38F, 1.43F, 1.48F, 1.35F, 1.7F, 1.42F, 2.0F, 1.26F, 3.0F, 1.5F, 2.0F
    };
    /**
     * Tolerance towards other cars
     * @author Omar Waly
     */
    static final float[] comprad = {
            0.5F, 0.4F, 0.8F, 0.5F, 0.4F, 0.5F, 0.5F, 0.5F, 0.5F, 0.8F, 0.5F, 1.5F, 0.5F, 0.8F, 0.5F, 0.8F
    };
    /**
     * How much a car can push another car while wasting
     * @author Omar Waly
     */
    static final int[] push = {
            2, 2, 3, 3, 2, 2, 2, 4, 2, 2, 2, 4, 2, 2, 2, 2
    };
    /**
     * The amount of "reverse push" or recoil the car will get from others
     * @author Omar Waly
     */
    static final int[] revpush = {
            2, 3, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 2, 2, 2, 1
    };
    /**
     * How much a car can lift another car while wasting
     * @author Omar Waly
     */
    static final int[] lift = {
            0, 30, 0, 20, 0, 30, 0, 0, 20, 0, 0, 0, 10, 0, 30, 0
    };
    /**
     * The amount of "reverse lift" or recoil the car will get from others
     * @author Omar Waly
     */
    static final int[] revlift = {
            0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 32
    };
    /**
     * The variable for power loss, higher means longer power duration.
     * @author Omar Waly
     */
    static final int[] powerloss = {
            2500000, 2500000, 3500000, 2500000, 4000000, 2500000, 3200000, 3200000, 2750000, 5500000, 2750000, 4500000,
            3500000, 16700000, 3000000, 5500000
    };
    /**
     * Y value of cars when flipped over during a bad landing
     * @author Omar Waly
     */
    static final int[] flipy = {
            -50, -60, -92, -44, -60, -57, -54, -60, -77, -57, -82, -85, -28, -100, -63, -127
    };
    /**
     * How badly a car can get smashed during wasting
     * @author Omar Waly
     */
    static final int[] msquash = {
            7, 4, 7, 2, 8, 4, 6, 4, 3, 8, 4, 10, 3, 20, 3, 8
    };
    /**
     * Collision radius
     *
     * "Clrad is the radius around each point that gets collided..
     * So if you have a car with so much points, you need the clrad
     * to be low or it will get damaged very fast as so many points
     * will get effected...If a car has low points you need the clrad
     * to be bigger so the damage reaches other points or else the car
     * will be indestructible..."
     * @author Omar Waly
     */
    static final int[] clrad = {
            3300, 1700, 4700, 3000, 2000, 4500, 3500, 5000, 10000, 15000, 4000, 7000, 10000, 15000, 5500, 5000
    };
    /**
     * Damage Multiplier
     * "The amount of damage based on the hit that should effect the points in clrad around the point of collision..."
     * @author Omar Waly
     */
    static final float[] dammult = {
            0.75F, 0.8F, 0.45F, 0.8F, 0.42F, 0.7F, 0.72F, 0.6F, 0.58F, 0.41F, 0.67F, 0.45F, 0.61F, 0.25F, 0.38F, 0.52F
    };
    /**
     * The amount of Damage that a car can take before it is considered as "wasted".
     * @author Omar Waly
     */
    static final int[] maxmag = {
            7600, 4200, 7200, 6000, 6000, 15000, 17200, 17000, 18000, 11000, 19000, 10700, 13000, 45000, 5800, 18000
    };

    static final int[] engine = {
            0, 1, 2, 1, 0, 3, 2, 2, 1, 0, 3, 4, 1, 4, 0, 3
    };

    static final float[] outdam = {
            0.67F, 0.35F, 0.8F, 0.5F, 0.42F, 0.76F, 0.82F, 0.76F, 0.72F, 0.62F, 0.79F, 0.95F, 0.77F, 1.0F, 0.85F, 1.0F
    };

    static float[] proba = {
            0.6F, 0.7F, 0.4F, 0.3F, 0.8F, 0, 0.3F, 0.3F, 0.3F, 0.1F, 0.1F, 0.5F, 0, 0, 0, 0
    };

    static final float[] dishandle = {
            0.65F, 0.6F, 0.55F, 0.77F, 0.62F, 0.9F, 0.6F, 0.72F, 0.45F, 0.8F, 0.95F, 0.4F, 0.87F, 0.42F, 1.0F, 0.95F
    };
}
