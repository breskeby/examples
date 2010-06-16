import com.breskeby.example.Cached

class CachedExample {

   @Cached
   public String calculateWithReturnLocalVariable(String input) {
     def output = input + System.nanoTime()
     return output
   }

   @Cached
   public String calculateWithReturnStatement(String input) {
     return(input + System.nanoTime())
   }

   @Cached
   public String calculateWithoutExplicitReturnStatement(String input) {
     input + System.nanoTime()
   }

   @Cached
   public String calculateWithoutExplicitReturnLocalVariable(String input) {
     def output = input + System.nanoTime()
     output
   }
}