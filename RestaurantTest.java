import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.Closeable;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");
    Restaurant restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//

    @Test
        public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
            Restaurant spiedRestaurant = Mockito.spy(restaurant);
            LocalTime aTimeDuringOpenHours = LocalTime.parse("16:00:00.000");
            Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(aTimeDuringOpenHours);

            spiedRestaurant.isRestaurantOpen();

            assertTrue(spiedRestaurant.isRestaurantOpen());
        }

        @Test
        public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
            Restaurant spiedRestaurant = Mockito.spy(restaurant);
            LocalTime aTimeDuringOpenHours = LocalTime.parse("05:00:00.000");
            Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(aTimeDuringOpenHours);

            spiedRestaurant.isRestaurantOpen();

            assertFalse(spiedRestaurant.isRestaurantOpen());

    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);

        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");

        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<MENU TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void when_menu_items_are_selected_display_order_total_by_adding_price_of_selected_Items(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        SelectedItem selectedItems = new SelectedItem;
        selectedItems.add("Sweet corn soup",119);
        selectedItems.add("Vegetable lasagne",269);

        assertEquals(388,selectedItems.getMenuTotal);

        //
    }

    @Test
    public void when_nothing_is_selected_no_total_is_shown(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertEquals(null,selectedItems.getMenuTotal);
    }

}