//
//  OffersSampleOfferViewController.h
//  OffersSampleApp
//
//  Created by Leonis&Co. on 2013/10/24.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface OffersSampleOfferViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>
@property (strong, nonatomic) UITableView *tbl_Offers;

@end
