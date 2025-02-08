package hu.soter.security;

public class AccessCoontrol {

    public static boolean hasPermission(User user, String action) {
        return switch (action) {
            case "ADD_BOOK", "DELETE_BOOK" -> user.getRole() == Role.ADMIN;
            case "LIST_BOOKS", "SEARCH_BOOK" -> user.getRole() == Role.ADMIN || user.getRole() == Role.USER;
            case "VIEW_BOOKS" -> true;
            default -> false;
        };
    }

    public static boolean canAddBook(User user) {
        return user.getRole() == Role.ADMIN;
    }

    public static boolean canRemoveBook(User user) {
        return user.getRole() == Role.ADMIN;
    }

    public static boolean canModifyBook(User user) {
        return user.getRole() == Role.ADMIN;
    }

    public static boolean canViewBooks(User user) {
        return user.getRole() == Role.ADMIN || user.getRole() == Role.USER || user.getRole() == Role.GUEST;
    }
}
