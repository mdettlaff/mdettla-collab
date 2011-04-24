package nibblr.domain;

import java.util.Set;

/**
 * Usługi związane z pobieraniem kanałów i wiadomości (niblów). W kodzie
 * produkcyjnym dostarczane są one przez agenta personalnego.
 * Interfejs ten oddziela warstwę agentową od warstwy GUI.
 */
public interface FeedService {

	/**
	 * Zwraca wszystkie kanały, które aktualnie można subskrybować.
	 * Ich lista wiadomości (niblów) jest zawsze pusta; do pobierania
	 * treści wiadomości służą inne metody tego serwisu.
	 */
	Set<Feed> getAllFeeds();

	// TODO dodać metody zwracające aktualną zawartość (wiadomości) kanałów
}
