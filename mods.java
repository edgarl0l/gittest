package mod;

class mods {
    public static boolean isPalindrome(int x) {
        if (x < 10) {
            return false; 
        }
        if (x % 10 == 0 && x != 0) {
            return false; 
        }

        int reverse = 0;
        while (x > reverse) {
            int num = x % 10;
            x /= 10;
            reverse = (reverse * 10) + num;
        }
        return x == reverse || x == reverse / 10; 
    }

    public static void main(String[] args) {
        int x = 20;
        System.out.println(isPalindrome(x)); 
    }
}
