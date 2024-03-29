package ru.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;
@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public Optional<Account> getById(int id) {
        return accounts.get(id) == null ? Optional.empty() : Optional.of(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> fromAccount = getById(fromId);
        Optional<Account> toAccount = getById(toId);
        if (fromAccount.isPresent() && toAccount.isPresent() && fromAccount.get().amount() >= amount) {
            update(new Account(fromId, fromAccount.get().amount() - amount));
            update(new Account(toId, toAccount.get().amount() + amount));
            return true;
        } else {
            return false;
        }
    }
}