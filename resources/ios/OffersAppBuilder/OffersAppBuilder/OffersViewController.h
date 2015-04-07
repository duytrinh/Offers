//
//  OffersViewController.h
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/3/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OffersViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>
@property (strong, nonatomic) UITableView *tbl_Offers;
@property int heighOfCell;

@end
