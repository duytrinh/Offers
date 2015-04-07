//
//  OfferRecommendViewController.h
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/2/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "ViewController.h"

@interface OfferRecommendViewController : ViewController <UITableViewDelegate, UITableViewDataSource>
@property (strong, nonatomic) IBOutlet UITableView *table_Recommend;
@property(nonatomic,strong) NSArray* itemList;

@end
