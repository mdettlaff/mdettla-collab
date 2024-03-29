package nibblr.service;

import java.util.Collection;
import java.util.List;

import nibblr.domain.Feed;

/**
 * Usługi związane z pobieraniem kanałów i wiadomości (niblów). W kodzie
 * produkcyjnym dostarczane są one przez agenta personalnego.
 * Interfejs ten oddziela warstwę agentową od warstwy GUI.
 */
public interface FeedService {

	/**
	 * Pobiera wszystkie kanały, które aktualnie można subskrybować.
	 * Ich lista wiadomości (niblów) jest zawsze pusta; do pobierania
	 * treści wiadomości służy metoda
	 * {@link #updateFeeds(Collection, FeedHandler)}.
	 *
	 * @param feedFoundHandler Funkcja zwrotna wywoływana po znalezieniu
	 *                         kanału.
	 */
	void downloadListOfAllFeeds(FeedHandler feedFoundHandler);

	/**
	 * Aktualizuje podane kanały (uzupełnia ich listę wiadomości).
	 *
	 * @param feedsToUpdate     Kanały, które zostaną zaktualizowane. Podana
	 *                          kolekcja nie jest modyfikowana.
	 * @param feedUpdateHandler Funkcja zwrotna wywoływana po aktualizacji
	 *                          kanału. Argumentem do niej jest zaktualizowany
	 *                          kanał.
	 */
	void updateFeeds(Collection<Feed> feedsToUpdate, FeedHandler feedUpdateHandler);

	/**
	 * Zwraca listę polecanych kanałów, posortowanych od najbardziej do
	 * najmniej polecanego. Najbardziej polecane są kanały podobne do tych,
	 * które użytkownik najczęściej aktualizuje.
	 */
	List<Feed> recommendFeeds();
}
