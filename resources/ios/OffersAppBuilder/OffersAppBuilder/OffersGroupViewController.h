//
//  OffersGroupViewController.h
//  TCISimpleOffers
//
//  Created by TTA-Brown-005 on 2/25/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OffersGroupViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *tbl_Group;

@end
