package nibblr.service;

import java.util.Collection;

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
	void downloadAllFeeds(FeedHandler feedFoundHandler);

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
}
