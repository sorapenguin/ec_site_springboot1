package com.example.ecsite.repository.purchase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ecsite.model.Purchase;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    // 必要に応じてユーザーごとの購入履歴取得メソッドを追加可能
}